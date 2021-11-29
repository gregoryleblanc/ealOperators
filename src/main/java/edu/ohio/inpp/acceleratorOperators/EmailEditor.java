package edu.ohio.inpp.acceleratorOperators;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import org.springframework.beans.factory.annotation.Autowired;

public class EmailEditor extends VerticalLayout implements KeyNotifier {

    private final EmailRepository emailRepository;

    // The email address under edit.
    private Email emailToEdit;

    EmailField emailAddress = new EmailField();
    TextField type = new TextField();

    // Action Buttons
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button reset = new Button("Reset");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());

    Binder<Email> binder = new Binder<>(Email.class);
    private ChangeHandler changeHandler;
    
    @Autowired
    public EmailEditor(EmailRepository eRepository) {
        HorizontalLayout actions = new HorizontalLayout();
        FormLayout formLayout = new FormLayout();
        this.emailRepository = eRepository;
        formLayout.addFormItem(emailAddress, "Email Address");
        formLayout.addFormItem(type, "Type/Description");
        actions.add(save, reset, delete);
        add(formLayout, actions);

        binder.bindInstanceFields(this);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        reset.addClickListener(e -> editEmail(emailToEdit));
        setVisible(false);
    }

    void delete() {
        emailRepository.delete(emailToEdit);
        changeHandler.onChange();
    }

    void save() {
        emailRepository.save(emailToEdit);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editEmail(Email em) {
        if (em == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = em.getId() != null;

        if (persisted) {
            emailToEdit = emailRepository.findById(em.getId()).get();
        }
        else {
            emailToEdit = em;
        }
        reset.setVisible(persisted);

        binder.setBean(emailToEdit);

        setVisible(true);

        emailAddress.focus();
    }

    public void setChangeHandler(ChangeHandler hand) {
        changeHandler = hand;
    }
}
