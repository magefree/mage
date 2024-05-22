package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.JunkToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DuchessWaywardTavernkeep extends CardImpl {

    public DuchessWaywardTavernkeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Hunters for Hire -- Whenever a creature you control deals combat damage to a player, put a quest counter on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.QUEST.createInstance()).setText("put a quest counter on it"),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false,
                SetTargetPointer.PERMANENT,
                true
        ).withFlavorWord("Hunters for Hire"));

        // {1}, Remove a quest counter from a permanent you control: Create a Junk token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new JunkToken()),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveCounterCost(new TargetControlledPermanent(), CounterType.QUEST));
        this.addAbility(ability);
    }

    private DuchessWaywardTavernkeep(final DuchessWaywardTavernkeep card) {
        super(card);
    }

    @Override
    public DuchessWaywardTavernkeep copy() {
        return new DuchessWaywardTavernkeep(this);
    }
}
