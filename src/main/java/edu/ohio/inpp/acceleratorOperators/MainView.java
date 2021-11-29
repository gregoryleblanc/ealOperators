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
    
    final Grid<Operator> operatorGrid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(OperatorRepository operatorRepository) {
        H1 heading = new H1("Operator Training Database");
    
        // List<Person> personList = new ArrayList<>();

        add(heading, new Text("Welcome to MainView by Greg."));

        // personList.add(new Person(100, "Lucas", "Kane", 68,
        //         new Addy("12080", "Washington"), "127-942-237"));
        // personList.add(new Person(101, "Peter", "Buchanan", 38,
        //         new Addy("93849", "New York"), "201-793-488"));
        // personList.add(new Person(102, "Samuel", "Lee", 53,
        //         new Addy("86829", "New York"), "043-713-538"));
        // personList.add(new Person(103, "Anton", "Ross", 37,
        //         new Addy("63521", "New York"), "150-813-6462"));
        // personList.add(new Person(104, "Aaron", "Atkinson", 18,
        //         new Addy("25415", "Washington"), "321-679-8544"));
        // personList.add(new Person(105, "Jack", "Woodward", 28,
        //         new Addy("95632", "New York"), "187-338-588"));
        
        // Grid<Person> grid = new Grid<>(Person.class);
        // grid.setItems(personList);
        
        // grid.removeColumnByKey("id");
        
        // // The Grid<>(Person.class) sorts the properties and in order to
        // // reorder the properties we use the 'setColumns' method.
        // grid.setColumns("firstName", "lastName", "age", "addy",
        //         "phoneNumber");
        // add(grid);


        this.operatorRepository = operatorRepository;
        this.operatorGrid = new Grid<>(Operator.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("Doesn't work.  Don't touch.", VaadinIcon.PLUS.create());
        
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, operatorGrid);
        operatorGrid.setHeight("300px");
        operatorGrid.setColumns("id", "firstName", "lastName", "loginName");
        // operatorGrid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        
        filter.setPlaceholder("Search by last name");
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listOperators(e.getValue()));

        listOperators(null);

    }

    void listOperators(String filterText) {
        if (!StringUtils.hasLength(filterText)){
            operatorGrid.setItems(operatorRepository.findAll());
        }
        else {
            operatorGrid.setItems(operatorRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}

