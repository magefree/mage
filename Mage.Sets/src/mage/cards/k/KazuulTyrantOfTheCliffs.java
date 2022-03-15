package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.OgreToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KazuulTyrantOfTheCliffs extends CardImpl {

    public KazuulTyrantOfTheCliffs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a creature an opponent controls attacks, if you're the defending player, create a 3/3 red Ogre creature token unless that creature's controller pays {3}.
        this.addAbility(new KazuulTyrantOfTheCliffsTriggeredAbility());
    }

    private KazuulTyrantOfTheCliffs(final KazuulTyrantOfTheCliffs card) {
        super(card);
    }

    @Override
    public KazuulTyrantOfTheCliffs copy() {
        return new KazuulTyrantOfTheCliffs(this);
    }
}

class KazuulTyrantOfTheCliffsTriggeredAbility extends TriggeredAbilityImpl {

    KazuulTyrantOfTheCliffsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KazuulTyrantOfTheCliffsEffect(new GenericManaCost(3)));
    }

    private KazuulTyrantOfTheCliffsTriggeredAbility(final KazuulTyrantOfTheCliffsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KazuulTyrantOfTheCliffsTriggeredAbility copy() {
        return new KazuulTyrantOfTheCliffsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!getControllerId().equals(game.getCombat().getDefendingPlayerId(event.getSourceId(), game))) {
            return false;
        }
        this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getControllerId(event.getSourceId())));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks, if you're the defending player, " +
                "create a 3/3 red Ogre creature token unless that creature's controller pays {3}.";
    }
}

class KazuulTyrantOfTheCliffsEffect extends OneShotEffect {

    protected Cost cost;
    private static OgreToken token = new OgreToken();

    KazuulTyrantOfTheCliffsEffect(Cost cost) {
        super(Outcome.PutCreatureInPlay);
        this.cost = cost;
    }

    private KazuulTyrantOfTheCliffsEffect(KazuulTyrantOfTheCliffsEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player payee = game.getPlayer(targetPointer.getFirst(game, source));
        if (payee == null) {
            return false;
        }
        cost.clearPaid();
        if (cost.pay(source, game, source, payee.getId(), false, null)) {
            return false;
        }
        return token.putOntoBattlefield(1, game, source, source.getControllerId());
    }

    @Override
    public KazuulTyrantOfTheCliffsEffect copy() {
        return new KazuulTyrantOfTheCliffsEffect(this);
    }
}
