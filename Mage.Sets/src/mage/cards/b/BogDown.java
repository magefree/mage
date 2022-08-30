package mage.cards.b;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BogDown extends CardImpl {

    public BogDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Kicker-Sacrifice two lands.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2,
                StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, true))));

        // Target player discards two cards. If Bog Down was kicked, that player discards three cards instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DiscardTargetEffect(3),
                new DiscardTargetEffect(2), KickedCondition.ONCE,
                "Target player discards two cards. If this spell was kicked, that player discards three cards instead."));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BogDown(final BogDown card) {
        super(card);
    }

    @Override
    public BogDown copy() {
        return new BogDown(this);
    }
}
