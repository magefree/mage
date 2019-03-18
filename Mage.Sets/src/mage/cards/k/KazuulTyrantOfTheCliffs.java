
package mage.cards.k;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OgreToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
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

    public KazuulTyrantOfTheCliffs(final KazuulTyrantOfTheCliffs card) {
        super(card);
    }

    @Override
    public KazuulTyrantOfTheCliffs copy() {
        return new KazuulTyrantOfTheCliffs(this);
    }
}

class KazuulTyrantOfTheCliffsTriggeredAbility extends TriggeredAbilityImpl {

    public KazuulTyrantOfTheCliffsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KazuulTyrantOfTheCliffsEffect(new GenericManaCost(3)));
    }

    public KazuulTyrantOfTheCliffsTriggeredAbility(final KazuulTyrantOfTheCliffsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KazuulTyrantOfTheCliffsTriggeredAbility copy() {
        return new KazuulTyrantOfTheCliffsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        Player defender = game.getPlayer(event.getTargetId());
        Player you = game.getPlayer(controllerId);
        if (!Objects.equals(attacker.getControllerId(), you.getId()) && Objects.equals(defender, you)) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(attacker.getControllerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks, if you're the defending player, create a 3/3 red Ogre creature token unless that creature's controller pays {3}";
    }
}

class KazuulTyrantOfTheCliffsEffect extends OneShotEffect {

    protected Cost cost;
    private static OgreToken token = new OgreToken();

    public KazuulTyrantOfTheCliffsEffect(Cost cost) {
        super(Outcome.PutCreatureInPlay);
        this.cost = cost;
    }

    public KazuulTyrantOfTheCliffsEffect(KazuulTyrantOfTheCliffsEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player payee = game.getPlayer(targetPointer.getFirst(game, source));
        if (payee != null) {
            cost.clearPaid();
            if (!cost.pay(source, game, source.getSourceId(), payee.getId(), false, null)) {
                return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
        }
        return false;
    }

    @Override
    public KazuulTyrantOfTheCliffsEffect copy() {
        return new KazuulTyrantOfTheCliffsEffect(this);
    }
}
