package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class SowerOfDiscord extends CardImpl {

    public SowerOfDiscord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Sower of Discord enters the battlefield, choose two players.
        this.addAbility(new AsEntersBattlefieldAbility(
                new SowerOfDiscordEntersBattlefieldEffect()
        ));

        // Whenever damage is dealt to one of the chosen players, the other chosen player also loses that much life.
        this.addAbility(new SowerOfDiscordTriggeredAbility());
    }

    private SowerOfDiscord(final SowerOfDiscord card) {
        super(card);
    }

    @Override
    public SowerOfDiscord copy() {
        return new SowerOfDiscord(this);
    }
}

class SowerOfDiscordEntersBattlefieldEffect extends OneShotEffect {

    public SowerOfDiscordEntersBattlefieldEffect() {
        super(Outcome.Damage);
        staticText = "choose two players";
    }

    private SowerOfDiscordEntersBattlefieldEffect(final SowerOfDiscordEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        TargetPlayer target = new TargetPlayer(2, 2, true);
        controller.chooseTarget(outcome, target, source, game);
        Player player1 = game.getPlayer(target.getFirstTarget());
        if (target.getTargets().size() <= 1) {
            return false;
        }
        Player player2 = game.getPlayer(target.getTargets().get(1));
        if (player1 == null || player2 == null) {
            return false;
        }
        game.getState().setValue(source.getSourceId() + "_player1", player1);
        game.getState().setValue(source.getSourceId() + "_player2", player2);
        game.informPlayers(permanent.getLogName() + ": "
                + controller.getLogName() + " has chosen "
                + player1.getLogName() + " and " + player2.getLogName()
        );
        permanent.addInfo(
                "chosen players",
                "<font color = 'blue'>Chosen players: "
                + player1.getName() + ", "
                + player2.getName() + "</font>", game
        );
        return true;
    }

    @Override
    public SowerOfDiscordEntersBattlefieldEffect copy() {
        return new SowerOfDiscordEntersBattlefieldEffect(this);
    }

}

class SowerOfDiscordTriggeredAbility extends TriggeredAbilityImpl {

    public SowerOfDiscordTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private SowerOfDiscordTriggeredAbility(final SowerOfDiscordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SowerOfDiscordTriggeredAbility copy() {
        return new SowerOfDiscordTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int damage = event.getAmount();
        Player player1 = (Player) game.getState().getValue(
                this.getSourceId() + "_player1"
        );
        Player player2 = (Player) game.getState().getValue(
                this.getSourceId() + "_player2"
        );
        if (player1 == null || player2 == null || damage == 0) {
            return false;
        }
        Effect effect = new LoseLifeTargetEffect(damage);
        if (event.getTargetId().equals(player1.getId())) {
            this.getEffects().clear();
            effect.setTargetPointer(new FixedTarget(player2.getId()));
            this.addEffect(effect);
            return true;
        } else if (event.getTargetId().equals(player2.getId())) {
            this.getEffects().clear();
            effect.setTargetPointer(new FixedTarget(player1.getId()));
            this.addEffect(effect);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever damage is dealt to one of the chosen players, "
                + "the other chosen player also loses that much life.";
    }
}
