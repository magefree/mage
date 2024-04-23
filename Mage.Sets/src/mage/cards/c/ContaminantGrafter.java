package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamagedBatchForPlayersEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author PurpleCrowbar
 */
public final class ContaminantGrafter extends CardImpl {

    public ContaminantGrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.PHYREXIAN, SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // Whenever one or more creatures you control deal combat damage to one or more players, proliferate.
        this.addAbility(new ContaminantGrafterTriggeredAbility());

        // Corrupted — At the beginning of your end step, if an opponent has three or more poison
        // counters, draw a card, then you may put a land card from your hand onto the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfYourEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                CorruptedCondition.instance, "At the beginning of your end step, if an opponent has three or more poison " +
                "counters, draw a card, then you may put a land card from your hand onto the battlefield"
        );
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A).concatBy("then"));
        ability.setAbilityWord(AbilityWord.CORRUPTED);
        ability.addHint(CorruptedCondition.getHint());
        this.addAbility(ability);
    }

    private ContaminantGrafter(final ContaminantGrafter card) {
        super(card);
    }

    @Override
    public ContaminantGrafter copy() {
        return new ContaminantGrafter(this);
    }
}

class ContaminantGrafterTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPlayerEvent> {

    ContaminantGrafterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ProliferateEffect(false), false);
        this.setTriggerPhrase("Whenever one or more creatures you control deal combat damage to one or more players, ");
    }

    private ContaminantGrafterTriggeredAbility(final ContaminantGrafterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_PLAYERS;
    }

    @Override
    public Stream<DamagedPlayerEvent> filterBatchEvent(GameEvent event, Game game) {
        return ((DamagedBatchForPlayersEvent) event)
                .getEvents()
                .stream()
                .filter(DamagedPlayerEvent::isCombatDamage)
                .filter(e -> e.getAmount() > 0)
                .filter(e -> Optional
                        .of(e)
                        .map(DamagedPlayerEvent::getSourceId)
                        .map(game::getPermanentOrLKIBattlefield)
                        .filter(p -> p.isControlledBy(getControllerId()))
                        .isPresent()
                );
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return filterBatchEvent(event, game).findAny().isPresent();
    }

    @Override
    public ContaminantGrafterTriggeredAbility copy() {
        return new ContaminantGrafterTriggeredAbility(this);
    }
}
