package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

public final class WickedReward extends CardImpl {

    public WickedReward(UUID cardId, CardSetInfo cardSetInfo) {
        super(cardId, cardSetInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        //As an additional cost to cast Wicked Reward, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        //Target creature gets +4/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public WickedReward(WickedReward other) {
        super(other);
    }

    public WickedReward copy() {
        return new WickedReward(this);
    }
}
