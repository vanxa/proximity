package com.vanxacloud.appstudio.proximity.fx.control.wizard.page;

import javafx.beans.binding.BooleanExpression;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

public abstract class WizardDialogPage {


    private final ObservableMap<String, Object> wizardState;

    private final MapChangeListener<String, Object> listener = this::stateChanged;


    public WizardDialogPage() {
        this.wizardState = FXCollections.observableHashMap();
        wizardState.addListener(listener);
    }


    public abstract BooleanExpression isValid();

    public void changeState(ObservableMap<String, Object> settings) {
        this.wizardState.putAll(settings);
    }


    abstract void stateChanged(MapChangeListener.Change<? extends String, ?> change);

}
