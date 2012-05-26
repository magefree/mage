package mage.abilities.costs.mana;

/**
 * This cost must be used only as optional mana cost in cards with Kicker
 * @author Loki
 */
public class KickerManaCost extends ManaCostsImpl {
    public KickerManaCost(String manaString) {
        super(manaString);
    }

    public KickerManaCost(final KickerManaCost cost) {
        super(cost);
    }

    @Override
    public KickerManaCost copy() {
        return new KickerManaCost(this);
    }

    @Override
    public String getText() {
        return "Kicker - " + super.getText();
    }
}
