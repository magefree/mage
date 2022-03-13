package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Landslide extends CardImpl {

    public Landslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Sacrifice any number of Mountains. Landslide deals that much damage to target player.
        this.getSpellAbility().addEffect(new LandslideEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
    }

    private Landslide(final Landslide card) {
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
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.MOUNTAIN.getPredicate());
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
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return false;
            }
            you.chooseTarget(Outcome.Detriment, target, source, game);
            if (!target.getTargets().isEmpty()) {
                int amount = 0;
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && permanent.sacrifice(source, game)) {
                        amount++;
                    }
                }
                Player player = game.getPlayer(source.getFirstTarget());
                if (player != null) {
                    player.damage(amount, source.getSourceId(), source, game);
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
