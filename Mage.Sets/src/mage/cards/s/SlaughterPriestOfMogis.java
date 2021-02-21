package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SlaughterPriestOfMogis extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or an enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    public SlaughterPriestOfMogis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you sacrifice a permanent, Slaughter-Priest of Mogis gets +2/+0 until end of turn.
        this.addAbility(new SlaughterPriestOfMogisAbility());

        // {2}, Sacrifice another creature or enchantment: Slaughter-Priest of Mogis gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private SlaughterPriestOfMogis(final SlaughterPriestOfMogis card) {
        super(card);
    }

    @Override
    public SlaughterPriestOfMogis copy() {
        return new SlaughterPriestOfMogis(this);
    }
}

class SlaughterPriestOfMogisAbility extends TriggeredAbilityImpl {

    SlaughterPriestOfMogisAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(2, 0, Duration.EndOfTurn));
    }

    private SlaughterPriestOfMogisAbility(final SlaughterPriestOfMogisAbility ability) {
        super(ability);
    }

    @Override
    public SlaughterPriestOfMogisAbility copy() {
        return new SlaughterPriestOfMogisAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you sacrifice a permanent, {this} gets +2/+0 until end of turn.";
    }
}
