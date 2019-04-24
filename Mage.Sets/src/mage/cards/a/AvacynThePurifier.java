
package mage.cards.a;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AvacynThePurifier extends CardImpl {

    public AvacynThePurifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.
        this.addAbility(new AvacynThePurifierAbility());
    }

    public AvacynThePurifier(final AvacynThePurifier card) {
        super(card);
    }

    @Override
    public AvacynThePurifier copy() {
        return new AvacynThePurifier(this);
    }
}

class AvacynThePurifierAbility extends TriggeredAbilityImpl {

    public AvacynThePurifierAbility() {
        super(Zone.BATTLEFIELD, new AvacynThePurifierEffect(), false);
    }

    public AvacynThePurifierAbility(final AvacynThePurifierAbility ability) {
        super(ability);
    }

    @Override
    public AvacynThePurifierAbility copy() {
        return new AvacynThePurifierAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORMED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            Permanent permanent = game.getPermanent(sourceId);
            if (permanent != null && permanent.isTransformed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever this creature transforms into Avacyn, the Purifier, it deals 3 damage to each other creature and each opponent.";
    }
}

class AvacynThePurifierEffect extends OneShotEffect {

    public AvacynThePurifierEffect() {
        super(Outcome.Damage);
    }

    public AvacynThePurifierEffect(final AvacynThePurifierEffect effect) {
        super(effect);
    }

    @Override
    public AvacynThePurifierEffect copy() {
        return new AvacynThePurifierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature");
        filter.add(AnotherPredicate.instance);
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(3, source.getSourceId(), game, false, true);
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.damage(3, source.getSourceId(), game, false, true);
            }
        }
        return true;
    }
}
