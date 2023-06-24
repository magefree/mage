package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
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
import mage.game.events.DamagedEvent;
import mage.game.events.DamagedPlayerBatchEvent;
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

class ContaminantGrafterTriggeredAbility extends TriggeredAbilityImpl {

    ContaminantGrafterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ProliferateEffect(false), false);
        this.setTriggerPhrase("Whenever one or more creatures you control deal combat damage to one or more players, ");
    }

    private ContaminantGrafterTriggeredAbility(final ContaminantGrafterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerBatchEvent dEvent = (DamagedPlayerBatchEvent) event;
        for (DamagedEvent damagedEvent : dEvent.getEvents()) {
            if (!damagedEvent.isCombatDamage()) {
                continue;
            }
            Permanent permanent = game.getPermanent(damagedEvent.getSourceId());
            if (permanent != null && permanent.isControlledBy(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ContaminantGrafterTriggeredAbility copy() {
        return new ContaminantGrafterTriggeredAbility(this);
    }
}
