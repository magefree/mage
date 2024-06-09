package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Pyramids extends CardImpl {
    
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a land");
    
    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(PyramidsPredicate.instance);
    }
    
    public Pyramids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {2}: Choose one
        // - Destroy target Aura attached to a land
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        // - The next time target land would be destroyed this turn, remove all damage marked on it instead.
        Mode mode = new Mode(new PyramidsEffect());
        mode.addTarget(new TargetLandPermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private Pyramids(final Pyramids card) {
        super(card);
    }

    @Override
    public Pyramids copy() {
        return new Pyramids(this);
    }
}

enum PyramidsPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachment = input.getObject();
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            return permanent != null && permanent.isLand(game);
        }
        return false;
    }

}

class PyramidsEffect extends ReplacementEffectImpl {

    PyramidsEffect() {
        super(Duration.EndOfTurn, Outcome.Regenerate);
        staticText = "the next time target land would be destroyed this turn, remove all damage marked on it instead";
    }

    private PyramidsEffect(final PyramidsEffect effect) {
        super(effect);
    }

    @Override
    public PyramidsEffect copy() {
        return new PyramidsEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.removeAllDamage(game);
        this.used = true;
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.DESTROY_PERMANENT == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(getTargetPointer().getFirst(game, source)) && !this.used;
    }

}
