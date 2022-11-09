package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SphinxsRevelation extends CardImpl {

    public SphinxsRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}{U}{U}");

        // You gain X life and draw X cards.
        ManacostVariableValue manaX = ManacostVariableValue.REGULAR;
        this.getSpellAbility().addEffect(new GainLifeEffect(manaX));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(manaX).concatBy("and"));
    }

    public SphinxsRevelation(final SphinxsRevelation card) {
        super(card);
    }

    @Override
    public SphinxsRevelation copy() {
        return new SphinxsRevelation(this);
    }
}