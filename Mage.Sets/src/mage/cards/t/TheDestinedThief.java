package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheDestinedThief extends CardImpl {

    public TheDestinedThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // The Destined Thief can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // {U}, {T}: Another target creature you control can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Whenever one or more creatures you control deal combat damage to one or more players, draw a card, then discard a card. If you have a full party, instead draw three cards.
        this.addAbility(new TheDestinedThiefTriggeredAbility());
    }

    private TheDestinedThief(final TheDestinedThief card) {
        super(card);
    }

    @Override
    public TheDestinedThief copy() {
        return new TheDestinedThief(this);
    }
}

class TheDestinedThiefTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    TheDestinedThiefTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3),
                new DrawDiscardControllerEffect(1, 1),
                FullPartyCondition.instance, "draw a card, then discard a card. " +
                "If you have a full party, instead draw three cards"
        ), true);
        setTriggerPhrase("Whenever one or more creatures you control deal combat damage to one or more players, ");
    }

    private TheDestinedThiefTriggeredAbility(final TheDestinedThiefTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        return event.isCombatDamage() && isControlledBy(game.getControllerId(event.getSourceId()));
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public TheDestinedThiefTriggeredAbility copy() {
        return new TheDestinedThiefTriggeredAbility(this);
    }
}
