package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HomingLightning extends CardImpl {

    public HomingLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");


        // Homing Lightning deals 4 damage to target creature and each other creature with the same name as that creature.
        this.getSpellAbility().addEffect(new HomingLightningEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HomingLightning(final HomingLightning card) {
        super(card);
    }

    @Override
    public HomingLightning copy() {
        return new HomingLightning(this);
    }
}

class HomingLightningEffect extends OneShotEffect {

    public HomingLightningEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 4 damage to target creature and each other creature with the same name as that creature";
    }

    private HomingLightningEffect(final HomingLightningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        if (CardUtil.haveEmptyName(targetPermanent)) {
            filter.add(new PermanentIdPredicate(targetPermanent.getId()));  // if no name (face down creature) only the creature itself is selected
        } else {
            filter.add(new NamePredicate(targetPermanent.getName()));
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            if (creature != null) {
                creature.damage(4, source.getSourceId(), source, game, false, true);
            }
        }
        return true;
    }

    @Override
    public HomingLightningEffect copy() {
        return new HomingLightningEffect(this);
    }
}