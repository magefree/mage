package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.GameEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HumanTorch extends CardImpl {

    public HumanTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, Human Torch gains flying, double strike, and haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ).setText("{this} gains flying")
        ).withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance);
        ability.addEffect(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText(", double strike"));
        ability.addEffect(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText(", and haste until end of turn."));
        this.addAbility(ability.addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // Whenever Human Torch attacks, you may pay {R}{G}{W}{U}. If you do, until end of turn, whenever he deals combat damage to an opponent, he deals that much damage to each other opponent.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CreateDelayedTriggeredAbilityEffect(new HumanTorchTriggeredAbility())
                        .setText("until end of turn, whenever he deals combat damage to an opponent, " +
                                "he deals that much damage to each other opponent"),
                new ManaCostsImpl<>("{R}{G}{W}{U}")
        )));
    }

    private HumanTorch(final HumanTorch card) {
        super(card);
    }

    @Override
    public HumanTorch copy() {
        return new HumanTorch(this);
    }
}

class HumanTorchTriggeredAbility extends DelayedTriggeredAbility {

    HumanTorchTriggeredAbility() {
        super(new HumanTorchEffect(), Duration.EndOfTurn, false, false);
        setTriggerPhrase("Whenever {this} deals combat damage to an opponent, ");
    }

    private HumanTorchTriggeredAbility(final HumanTorchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HumanTorchTriggeredAbility copy() {
        return new HumanTorchTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())
                || !((DamagedBatchBySourceEvent) event).isCombatDamage()
                || !game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setValue("playerId", event.getTargetId());
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }
}

class HumanTorchEffect extends OneShotEffect {

    HumanTorchEffect() {
        super(Outcome.Benefit);
        staticText = "he deals that much damage to each other opponent";
    }

    private HumanTorchEffect(final HumanTorchEffect effect) {
        super(effect);
    }

    @Override
    public HumanTorchEffect copy() {
        return new HumanTorchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) getValue("playerId");
        int damage = (Integer) getValue("damage");
        if (damage < 1) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            if (!opponentId.equals(playerId)) {
                Optional.ofNullable(opponentId)
                        .map(game::getPlayer)
                        .ifPresent(player -> player.damage(damage, source, game));
            }
        }
        return true;
    }
}
