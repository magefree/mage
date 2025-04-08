package mage.cards.h;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author androosss
 */
public final class HollowmurkSiege extends CardImpl {

    public HollowmurkSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{B}{G}");

        // As this enchantment enters, choose Sultai or Abzan.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Sultai or Abzan?", "Sultai", "Abzan"), null,
                "As {this} enters, choose Sultai or Abzan.", ""));

        // * Sultai -- Whenever a counter is put on a creature you control, draw a card.
        // This ability triggers only once each turn.
        this.addAbility(new HollowmurkSiegeSultaiTriggeredAbility());

        // * Abzan -- Whenever you attack, put a +1/+1 counter on target attacking
        // creature. It gains menace until end of turn.
        TriggeredAbility abzanAbility = new AttacksWithCreaturesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(1)), 1);
        abzanAbility.addEffect(
                new GainAbilityTargetEffect(new MenaceAbility()));
        abzanAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_ATTACKING_CREATURE));

        this.addAbility(new ConditionalTriggeredAbility(
                abzanAbility,
                new ModeChoiceSourceCondition("Abzan"),
                "&bull; Abzan &mdash; Whenever you attack, put a +1/+1 counter on target attacking creature. It gains menace until end of turn.")
                .setTriggersLimitEachTurn(1));
    }

    private HollowmurkSiege(final HollowmurkSiege card) {
        super(card);
    }

    @Override
    public HollowmurkSiege copy() {
        return new HollowmurkSiege(this);
    }
}

class HollowmurkSiegeSultaiTriggeredAbility extends TriggeredAbilityImpl {
    private static ModeChoiceSourceCondition choiceCondition = new ModeChoiceSourceCondition("Sultai");

    HollowmurkSiegeSultaiTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        setTriggersLimitEachTurn(1);
    }

    private HollowmurkSiegeSultaiTriggeredAbility(final HollowmurkSiegeSultaiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HollowmurkSiegeSultaiTriggeredAbility copy() {
        return new HollowmurkSiegeSultaiTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!choiceCondition.apply(game, this)) {
            return false;
        }
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = Optional
                    .ofNullable(game.getPermanentOrLKIBattlefield(event.getTargetId()))
                    .orElse(game.getPermanentEntering(event.getTargetId()));

            return permanent != null && permanent.isCreature(game) && permanent.getControllerId() == getControllerId();
        }
        return false;
    }

    @Override
    public String getRule() {
        return "&bull; Sultai &mdash; Whenever a counter is put on a creature you control, draw a card. This ability triggers only once each turn.";
    }
}
