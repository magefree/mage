package mage.cards.g;

import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class GruesomeDiscovery extends CardImpl {

    public GruesomeDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Target player discards two cards.
        // <i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, you choose two cards from it, then that player discards those cards.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardCardYouChooseTargetEffect(2, StaticFilters.FILTER_CARD_CARDS), new DiscardTargetEffect(2),
                MorbidCondition.instance, "Target player discards two cards. " +
                "<br><i>Morbid</i> &mdash; If a creature died this turn, instead that player reveals their hand, " +
                "you choose two cards from it, then that player discards those cards"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addHint(MorbidHint.instance);
    }

    private GruesomeDiscovery(final GruesomeDiscovery card) {
        super(card);
    }

    @Override
    public GruesomeDiscovery copy() {
        return new GruesomeDiscovery(this);
    }
}
