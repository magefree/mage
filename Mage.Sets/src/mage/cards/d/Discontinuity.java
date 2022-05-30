package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.EndTurnEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Discontinuity extends CardImpl {

    public Discontinuity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{U}");

        // As long as it's your turn, this spell costs {2}{U}{U} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(
                new ManaCostsImpl<>("{2}{U}{U}"), MyTurnCondition.instance
        ).setText("as long as it's your turn, this spell costs {2}{U}{U} less to cast"))
                .setRuleAtTheTop(true)
                .addHint(MyTurnHint.instance));

        // End the turn.
        this.getSpellAbility().addEffect(new EndTurnEffect());
    }

    private Discontinuity(final Discontinuity card) {
        super(card);
    }

    @Override
    public Discontinuity copy() {
        return new Discontinuity(this);
    }
}
