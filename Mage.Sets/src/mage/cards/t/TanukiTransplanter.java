package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReconfigureAbility;
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
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TanukiTransplanter extends CardImpl {

    public TanukiTransplanter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Tanuki Transplanter or equipped creature attacks, add an amount of {G} equal to its power. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new TanukiTransplanterTriggeredAbility());

        // Reconfigure {3}
        this.addAbility(new ReconfigureAbility("{3}"));
    }

    private TanukiTransplanter(final TanukiTransplanter card) {
        super(card);
    }

    @Override
    public TanukiTransplanter copy() {
        return new TanukiTransplanter(this);
    }
}

class TanukiTransplanterTriggeredAbility extends TriggeredAbilityImpl {

    TanukiTransplanterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TanukiTransplanterEffect());
    }

    private TanukiTransplanterTriggeredAbility(final TanukiTransplanterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TanukiTransplanterTriggeredAbility copy() {
        return new TanukiTransplanterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID attacker;
        if (!game.getCombat().getAttackers().contains(getSourceId())) {
            Permanent permanent = getSourcePermanentOrLKI(game);
            if (permanent != null && game.getCombat().getAttackers().contains(permanent.getAttachedTo())) {
                attacker = permanent.getAttachedTo();
            } else {
                attacker = null;
            }
        } else {
            attacker = getSourceId();
        }
        if (attacker == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(attacker, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or equipped creature attacks, add an amount of {G} equal to its power. " +
                "Until end of turn, you don't lose this mana as steps and phases end.";
    }
}

class TanukiTransplanterEffect extends OneShotEffect {

    TanukiTransplanterEffect() {
        super(Outcome.Benefit);
    }

    private TanukiTransplanterEffect(final TanukiTransplanterEffect effect) {
        super(effect);
    }

    @Override
    public TanukiTransplanterEffect copy() {
        return new TanukiTransplanterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || permanent.getPower().getValue() < 1) {
            return false;
        }
        player.getManaPool().addMana(Mana.GreenMana(permanent.getPower().getValue()), game, source, true);
        return true;
    }
}
