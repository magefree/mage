
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author spjspj
 */
public final class SanguineSacrament extends CardImpl {

    public SanguineSacrament(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{W}");

        // You gain twice X life. Put Sanguine Sacrament on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new GainLifeEffect(new SanguineSacramentValue()));
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    public SanguineSacrament(final SanguineSacrament card) {
        super(card);
    }

    @Override
    public SanguineSacrament copy() {
        return new SanguineSacrament(this);
    }
}

class SanguineSacramentValue extends ManacostVariableValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return super.calculate(game, sourceAbility, effect) * 2;
    }

    @Override
    public SanguineSacramentValue copy() {
        return new SanguineSacramentValue();
    }

    @Override
    public String toString() {
        return "twice X";
    }
}
