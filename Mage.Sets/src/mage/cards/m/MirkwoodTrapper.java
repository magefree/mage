package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PlayerAttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MirkwoodTrapper extends CardImpl {

    public MirkwoodTrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever a player attacks you, target attacking creature gets -2/-0 until end of turn.
        this.addAbility(new MirkwoodTrapperTriggerAttackYou());

        // Whenever a player attacks, if they aren't attacking you, that player chooses an attacking creature. It gets +2/+0 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
            new PlayerAttacksTriggeredAbility(new MirkwoodTrapperEffect(), true),
            NotAttackingSourceControllerCondition.instance,
            "Whenever a player attacks, if they aren't attacking you, that player chooses an attacking creature. It gets +2/+0 until end of turn."
        ));
    }

    private MirkwoodTrapper(final MirkwoodTrapper card) {
        super(card);
    }

    @Override
    public MirkwoodTrapper copy() {
        return new MirkwoodTrapper(this);
    }
}

class MirkwoodTrapperTriggerAttackYou extends TriggeredAbilityImpl {

    MirkwoodTrapperTriggerAttackYou() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(-2, 0), false);
        this.addTarget(new TargetAttackingCreature());
        this.setTriggerPhrase("whenever a player attacks you, ");
    }

    private MirkwoodTrapperTriggerAttackYou(final MirkwoodTrapperTriggerAttackYou ability) {
        super(ability);
    }

    @Override
    public MirkwoodTrapperTriggerAttackYou copy() {
        return new MirkwoodTrapperTriggerAttackYou(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return getControllerId().equals(event.getTargetId());
    }
}

enum NotAttackingSourceControllerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat()
            .getPlayerDefenders(game, false)
            .stream()
            .noneMatch(pId -> pId.equals(source.getControllerId()));
    }

    @Override
    public String toString() {
        return "they aren't attacking you";
    }
}

class MirkwoodTrapperEffect extends OneShotEffect {

    MirkwoodTrapperEffect() {
        super(Outcome.BoostCreature);
        this.setText("that player chooses an attacking creature. It gets +2/+0 until end of turn");
    }

    private MirkwoodTrapperEffect(final MirkwoodTrapperEffect ability) {
        super(ability);
    }

    @Override
    public MirkwoodTrapperEffect copy() {
        return new MirkwoodTrapperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }

        TargetAttackingCreature target = new TargetAttackingCreature(1, 1, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());

        if (permanent == null) {
            return false;
        }

        if (player != null) {
            game.informPlayers(player.getLogName() + " chose " + permanent.getLogName() + ".");
        }

        BoostTargetEffect boost = new BoostTargetEffect(2, 0);
        boost.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(boost, source);

        return true;
    }
}