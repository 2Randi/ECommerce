package fr.montpellier.myecommerce;


import fr.montpellier.myecommerce.db.entity.Interest;

public class InterestCheckbox {
    private final Interest interest;
    private boolean selected;

    public InterestCheckbox(Interest interest){
        this.interest = interest;
        this.selected = false;
    }

    public Interest getInterest() {
        return interest;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
