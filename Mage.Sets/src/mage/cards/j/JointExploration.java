package mage.cards.j;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class JointExploration extends CardImpl {

    public JointExploration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Kicker {G}
        this.addAbility(new KickerAbility("{G}"));

        // Scry 2, then draw a card. If this spell was kicked, you may put a land card from your hand onto the battlefield.
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A),
                KickedCondition.ONCE,
                "If this spell was kicked, you may put a land card from your hand onto the battlefield."
        ));
    }

    private JointExploration(final JointExploration card) {
        super(card);
    }

    @Override
    public JointExploration copy() {
        return new JointExploration(this);
    }
}
