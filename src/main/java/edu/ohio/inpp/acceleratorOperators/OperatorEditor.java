package edu.ohio.inpp.acceleratorOperators;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class OperatorEditor extends VerticalLayout implements KeyNotifier {
    
    private final OperatorRepository operatorRepository;

    // The currently edited customer
    private Operator operator;

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField loginName = new TextField();
    DateTimePicker updated = new DateTimePicker();
    DateTimePicker created = new DateTimePicker();
    Checkbox active = new Checkbox();
    Set<Email> email = new HashSet<Email>();

    // Action buttons
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button reset = new Button("Reset");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    Button addEmail = new Button("New email", VaadinIcon.PLUS.create());
    
    Binder<Operator> binder = new Binder<>(Operator.class);
    private ChangeHandler changeHandler;

    @Autowired
    public OperatorEditor(OperatorRepository opRepository) {
        HorizontalLayout actions = new HorizontalLayout();
        FormLayout formLayout = new FormLayout();

        this.operatorRepository = opRepository;

        // Set up the labels for the form fields, and add them to the form.
        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        loginName.setLabel("Login Name");
        loginName.getElement().setEnabled(false);
        active.setLabel("Active");
        updated.setLabel("Last Updated");
        updated.getElement().setEnabled(false);
        created.setLabel("Operator Created");
        created.getElement().setEnabled(false);

        formLayout.add(firstName, lastName, loginName, active, updated, created);

        actions.add(save, reset, delete);
        save.getStyle().set("marginRight", "10px");

        add(formLayout, addEmail, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        // setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete, and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        reset.addClickListener(e -> editOperator(operator));
        setVisible(false);
    }

    void delete() {
        operatorRepository.delete(operator);
        changeHandler.onChange();
    }

    void save() {
        operator.setUpdated(LocalDateTime.now());
        operatorRepository.save(operator);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editOperator(Operator op) {
        if (op == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = op.getId() != null;

        if (persisted) {
            operator = operatorRepository.findById(op.getId()).get();
        }
        else {
            operator = op;
        }
        reset.setVisible(persisted);

        // Bind operator properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(operator);

        setVisible(true);

        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler hand) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = hand;
    }

    // This sets lets us create the active column as a checkbox.
    // Doesn't let us save it properly yet.
    private Component createActiveCheckbox(Operator myBean) {
        Checkbox checkbox = new Checkbox();
            checkbox.setValue(myBean.getActive());
            checkbox.addClickListener(activeListener -> myBean.setActive(checkbox.getValue()));
            operatorRepository.save(myBean);
            // this.operatorGrid.getDataProvider().refreshItem(myBean);
            return checkbox;
    }
}
