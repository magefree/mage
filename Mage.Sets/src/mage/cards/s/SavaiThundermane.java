package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavaiThundermane extends CardImpl {

    public SavaiThundermane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cycle a card, you may pay {2}. When you do Savai Thundermane deals 2 damage to target creature and you gain 2 life.
        this.addAbility(new CycleControllerTriggeredAbility(
                new DoIfCostPaid(
                        new SavaiThundermaneCreateReflexiveTriggerEffect(), new GenericManaCost(2),
                        "Pay {2} to deal 2 damage and gain 2 life?"
                ).setText("you may pay {2}. When you do, {this} deals 2 damage to target creature and you gain 2 life")
        ));
    }

    private SavaiThundermane(final SavaiThundermane card) {
        super(card);
    }

    @Override
    public SavaiThundermane copy() {
        return new SavaiThundermane(this);
    }
}

class SavaiThundermaneCreateReflexiveTriggerEffect extends OneShotEffect {

    SavaiThundermaneCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private SavaiThundermaneCreateReflexiveTriggerEffect(final SavaiThundermaneCreateReflexiveTriggerEffect effect) {
        super(effect);
    }

    @Override
    public SavaiThundermaneCreateReflexiveTriggerEffect copy() {
        return new SavaiThundermaneCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new SavaiThundermaneReflexiveTriggeredAbility(), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class SavaiThundermaneReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    SavaiThundermaneReflexiveTriggeredAbility() {
        super(new DamageTargetEffect(2), Duration.OneUse, true);
        this.addEffect(new GainLifeEffect(2));
        this.addTarget(new TargetCreaturePermanent());
    }

    private SavaiThundermaneReflexiveTriggeredAbility(final SavaiThundermaneReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SavaiThundermaneReflexiveTriggeredAbility copy() {
        return new SavaiThundermaneReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "{this} deals 2 damage to target creature and you gain 2 life";
    }
}
