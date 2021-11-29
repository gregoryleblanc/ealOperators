package edu.ohio.inpp.acceleratorOperators;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;
import com.vaadin.flow.data.binder.HasDataProvider;
// com.vaadin.flow.data.binder.HasDataProvider.setItems


import java.util.List;
import java.util.ArrayList;


@Route
public class MainView extends VerticalLayout{

    private final OperatorRepository operatorRepository;

    private final OperatorEditor operatorEditor;
    
    final Grid<Operator> operatorGrid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(OperatorRepository opRepository, OperatorEditor opEditor) {
        
        // Set the page H1 header.
        H1 heading = new H1("Operator Training Database");
    
        //Add some  sub heading text.
        add(heading, new Text("Welcome to MainView by Greg."));

        // I think this is plumbing.
        this.operatorRepository = opRepository;
        this.operatorEditor = opEditor;
        this.operatorGrid = new Grid<>(Operator.class);
        this.filter = new TextField();

        // This creates a button with a + to add new operators
        this.addNewBtn = new Button("Doesn't work.  Don't touch.", VaadinIcon.PLUS.create());
        
        // This lays out the search by last name and add new buttons.
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        
        // This adds the actions (above), the grid for operators, and the
        // operator editor.
        add(actions, operatorGrid, opEditor);
        operatorGrid.setHeight("300px");
        operatorGrid.setColumns("id", "firstName", "lastName", "loginName");
        // Make the ID column fixed width.
        operatorGrid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Set up the filter box and plumb it in.
        filter.setPlaceholder("Search by last name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listOperators(e.getValue()));

        // Connect selected operator to editor or hide if none selected
        operatorGrid.asSingleSelect().addValueChangeListener(e -> {
            opEditor.editOperator(e.getValue());
        });

        // Listen for changes made by the editor, and refresh data from
        // the backend.

        opEditor.setChangeHandler(() -> {
            opEditor.setVisible(false);
            listOperators(filter.getValue());;
        });

        listOperators(null);

    }

    // This sets up the operator listing, and enables the filter functionality.
    void listOperators(String filterText) {
        if (!StringUtils.hasLength(filterText)){
            operatorGrid.setItems(operatorRepository.findAll());
        }
        else {
            operatorGrid.setItems(operatorRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}

