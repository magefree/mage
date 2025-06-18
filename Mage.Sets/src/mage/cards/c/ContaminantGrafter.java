package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
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
import mage.game.permanent.Permanent;

import java.util.UUID;

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
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(CorruptedCondition.instance);
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A).concatBy(", then"));
        this.addAbility(ability.setAbilityWord(AbilityWord.CORRUPTED).addHint(CorruptedCondition.getHint()));
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
    public boolean checkEvent(DamagedPlayerEvent event, Game game) {
        if (!event.isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((DamagedBatchForPlayersEvent) event, game).isEmpty();
    }

    @Override
    public ContaminantGrafterTriggeredAbility copy() {
        return new ContaminantGrafterTriggeredAbility(this);
    }
}
