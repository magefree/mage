package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class AlongTheCrookedWay extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("goblins and Orcs you control");

    static {
        filter.add(Predicates.or(
            SubType.GOBLIN.getPredicate(),
            SubType.ORC.getPredicate()
        ));
    }

    public AlongTheCrookedWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // When this enchantment enters, return target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Whenever a creature card leaves your graveyard, amass Goblins 1.
        this.addAbility(new AlongTheCrookedWayTriggeredAbility());

        // {1}{B}: Goblins and Orcs you control gain menace until end of turn.
        Ability activatedAbility = new SimpleActivatedAbility(
            new GainAbilityControlledEffect(
                new MenaceAbility(),
                Duration.EndOfTurn,
                filter
            ), new ManaCostsImpl<>("{1}{B}"));
        this.addAbility(activatedAbility);
    }

    private AlongTheCrookedWay(final AlongTheCrookedWay card) {
        super(card);
    }

    @Override
    public AlongTheCrookedWay copy() {
        return new AlongTheCrookedWay(this);
    }
}

class AlongTheCrookedWayTriggeredAbility extends TriggeredAbilityImpl {

    public AlongTheCrookedWayTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AmassEffect(1, SubType.GOBLIN));
        setTriggerPhrase("Whenever a creature card leaves your graveyard, ");
    }

    private AlongTheCrookedWayTriggeredAbility(final AlongTheCrookedWayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AlongTheCrookedWayTriggeredAbility copy() {
        return new AlongTheCrookedWayTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(zEvent.getTargetId());
            return card != null && card.isCreature(game) && card.getOwnerId().equals(getControllerId());
        }
        return false;
    }
}
