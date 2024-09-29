package mage.cards.h;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{G}", "Bakery Raid", "{G}");
        
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a Food: Hollow Scavenger gets +2/+2 until end of turn. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD));
        this.addAbility(ability);

        // Bakery Raid
        // Create a Food token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken()));

        this.finalizeAdventure();
    }

    private HollowScavenger(final HollowScavenger card) {
        super(card);
    }

    @Override
    public HollowScavenger copy() {
        return new HollowScavenger(this);
    }
}
