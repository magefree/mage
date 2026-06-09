package mage.cards.g;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostLantern extends AdventureCard {

    public GhostLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{B}",
                "Bind Spirit",
                new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Whenever a creature you control dies, put a +1/+1 counter on equipped creature.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersAttachedEffect(
                CounterType.P1P1.createInstance(), "equipped creature"
        ), false, StaticFilters.FILTER_CONTROLLED_A_CREATURE));

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));

        // Bind Spirit
        // Return target creature card from your graveyard to your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        finalizeCard();
    }

    private GhostLantern(final GhostLantern card) {
        super(card);
    }

    @Override
    public GhostLantern copy() {
        return new GhostLantern(this);
    }
}
