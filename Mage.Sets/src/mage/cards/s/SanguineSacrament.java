
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class SanguineSacrament extends CardImpl {

    public SanguineSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // You gain twice X life. Put Sanguine Sacrament on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new GainLifeEffect(SanguineSacramentValue.instance));
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private SanguineSacrament(final SanguineSacrament card) {
        super(card);
    }

    @Override
    public SanguineSacrament copy() {
        return new SanguineSacrament(this);
    }
}

enum SanguineSacramentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return sourceAbility.getManaCostsToPay().getX() * 2;
    }

    @Override
    public SanguineSacramentValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "twice X";
    }
}
