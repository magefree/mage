package mage.abilities.effects.common;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.game.Game;

public class BasicManaEffect extends ManaEffect {

    protected Mana mana;

    public BasicManaEffect(Mana mana) {
        super();
        this.mana = mana;
        staticText = "add " + mana.toString() + " to your mana pool";
    }

    public BasicManaEffect(ConditionalMana conditionalMana) {
        super();
        this.mana = conditionalMana;
        staticText = "add " + mana.toString() + " to your mana pool. " + conditionalMana.getDescription();
    }

    public BasicManaEffect(final BasicManaEffect effect) {
        super(effect);
        this.mana = effect.mana.copy();
    }

    @Override
    public BasicManaEffect copy() {
        return new BasicManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getPlayer(source.getControllerId()).getManaPool().addMana(mana, game, source);
        return true;
    }

    public Mana getMana() {
        return mana;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return mana;
    }
}
