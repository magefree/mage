package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
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

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BronzeBombshell extends CardImpl {

    public BronzeBombshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When a player other than Bronze Bombshell's owner controls it, that player sacrifices it. If the player does, Bronze Bombshell deals 7 damage to the player.
        this.addAbility(new LoseControlTriggeredAbility(new BronzeBombshellEffect(), false));

    }

    private BronzeBombshell(final BronzeBombshell card) {
        super(card);
    }

    @Override
    public BronzeBombshell copy() {
        return new BronzeBombshell(this);
    }
}

class LoseControlTriggeredAbility extends TriggeredAbilityImpl {

    public LoseControlTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("When a player other than {this}'s owner controls it, ");
    }

    public LoseControlTriggeredAbility(final LoseControlTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoseControlTriggeredAbility copy() {
        return new LoseControlTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent sourcePermanent = game.getPermanent(event.getTargetId());
            if (sourcePermanent != null) {
                return !(sourcePermanent.getControllerId()).equals(sourcePermanent.getOwnerId());
            }
        }
        return false;
    }
}

class BronzeBombshellEffect extends OneShotEffect {

    public BronzeBombshellEffect() {
        super(Outcome.Damage);
        this.staticText = "that player sacrifices it. If the player does, {this} deals 7 damage to the player.";
    }

    public BronzeBombshellEffect(final BronzeBombshellEffect effect) {
        super(effect);
    }

    @Override
    public BronzeBombshellEffect copy() {
        return new BronzeBombshellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent bronzeBombshell = game.getPermanent(source.getSourceId());
        if (bronzeBombshell != null) {
            Player newController = game.getPlayer(bronzeBombshell.getControllerId());
            if (newController != null) {
                if (bronzeBombshell.sacrifice(source, game)) {//sacrificed by the new controlling player
                    newController.damage(7, source.getSourceId(), source, game);//bronze bombshell does 7 damage to the controller
                    return true;
                }
            }
        }
        return false;
    }
}
