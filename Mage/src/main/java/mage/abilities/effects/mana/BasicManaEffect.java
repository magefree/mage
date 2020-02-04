package mage.abilities.effects.mana;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.ManaEffect;
import mage.game.Game;

public class BasicManaEffect extends ManaEffect {

    protected Mana manaTemplate;

    public BasicManaEffect(Mana mana) {
        super();
        this.manaTemplate = mana;
        staticText = "add " + mana.toString();
    }

    public BasicManaEffect(ConditionalMana conditionalMana) {
        super();
        this.manaTemplate = conditionalMana;
        staticText = "add " + manaTemplate.toString() + " " + conditionalMana.getDescription();
    }

    public BasicManaEffect(final BasicManaEffect effect) {
        super(effect);
        this.manaTemplate = effect.manaTemplate.copy();

    }

    @Override
    public BasicManaEffect copy() {
        return new BasicManaEffect(this);
    }

    public Mana getManaTemplate() {
        return manaTemplate;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return manaTemplate.copy();
    }

}
