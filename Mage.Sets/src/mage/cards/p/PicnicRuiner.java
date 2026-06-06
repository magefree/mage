package mage.cards.p;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PicnicRuiner extends AdventureCard {

    public PicnicRuiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOBLIN, SubType.ROGUE}, "{1}{R}",
                "Stolen Goodies",
                new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Picnic Ruiner
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever Picnic Ruiner attacks while you control a creature with power 4 or greater, Picnic Ruiner gains double strike until end of turn.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), false
        ).withTriggerCondition(FerociousCondition.instance));

        // Stolen Goodies
        // Distribute three +1/+1 counters among any number of target creatures you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new DistributeCountersEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(
                new TargetCreaturePermanentAmount(3, 0, 3, StaticFilters.FILTER_CONTROLLED_CREATURES)
        );

        finalizeCard();
    }

    private PicnicRuiner(final PicnicRuiner card) {
        super(card);
    }

    @Override
    public PicnicRuiner copy() {
        return new PicnicRuiner(this);
    }
}
