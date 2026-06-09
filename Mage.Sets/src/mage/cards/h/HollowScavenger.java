package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HollowScavenger extends AdventureCard {

    public HollowScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WOLF}, "{2}{G}",
                "Bakery Raid",
                new CardType[]{CardType.SORCERY}, "{G}");

        // Hollow Scavenger
        this.getLeftHalfCard().setPT(3, 2);

        // {1}, Sacrifice a Food: Hollow Scavenger gets +2/+2 until end of turn. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD));
        this.getLeftHalfCard().addAbility(ability);

        // Bakery Raid
        // Create a Food token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));

        finalizeCard();
    }

    private HollowScavenger(final HollowScavenger card) {
        super(card);
    }

    @Override
    public HollowScavenger copy() {
        return new HollowScavenger(this);
    }
}
