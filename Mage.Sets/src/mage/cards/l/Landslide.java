
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author L_J
 */
public final class Landslide extends CardImpl {

    public Landslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Sacrifice any number of Mountains. Landslide deals that much damage to target player.
        this.getSpellAbility().addEffect(new LandslideEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    public Landslide(final Landslide card) {
        super(card);
    }

    @Override
    public Landslide copy() {
        return new Landslide(this);
    }
}

class LandslideEffect extends OneShotEffect {

    static final FilterPermanent filter = new FilterPermanent("Mountains to sacrifice");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public LandslideEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice any number of Mountains. {this} deals that much damage to target player or planeswalker";
    }

    public LandslideEffect(final LandslideEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
            if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
            you.chooseTarget(Outcome.Detriment, target, source, game);
            if (!target.getTargets().isEmpty()) {
                int amount = 0;
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.sacrifice(source.getSourceId(), game)) {
                        amount++;
                    }
                }
                Player player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    player.damage(amount, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LandslideEffect copy() {
        return new LandslideEffect(this);
    }
}
