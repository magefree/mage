package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastAnotherSpellThisTurnCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jmlundeen
 */
public final class FocusTheMind extends CardImpl {

    public FocusTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");
        

        // This spell costs {2} less to cast if you've cast another spell this turn.
        Effect effect = new SpellCostReductionSourceEffect(2, CastAnotherSpellThisTurnCondition.instance)
                .setCanWorksOnStackOnly(true);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, effect)
                .setRuleAtTheTop(true)
                .addHint(CastAnotherSpellThisTurnCondition.instance.getHint()));

        // Draw three cards, then discard a card.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(3, 1));
    }

    private FocusTheMind(final FocusTheMind card) {
        super(card);
    }

    @Override
    public FocusTheMind copy() {
        return new FocusTheMind(this);
    }
}
