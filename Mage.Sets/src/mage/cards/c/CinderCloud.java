package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class CinderCloud extends CardImpl {

    public CinderCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Destroy target creature. If a white creature dies this way, Cinder Cloud deals damage to that creature's controller equal to the creature's power.
        this.getSpellAbility().addEffect(new CinderCloudEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CinderCloud(final CinderCloud card) {
        super(card);
    }

    @Override
    public CinderCloud copy() {
        return new CinderCloud(this);
    }
}

class CinderCloudEffect extends OneShotEffect {

    public CinderCloudEffect() {
        super(Outcome.Benefit);
        this.staticText = "Destroy target creature. If a white creature dies this way, " +
                "{this} deals damage to that creature's controller equal to the creature's power";
    }

    private CinderCloudEffect(final CinderCloudEffect effect) {
        super(effect);
    }

    @Override
    public CinderCloudEffect copy() {
        return new CinderCloudEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && permanent.destroy(source, game, false) && permanent.getColor(game).equals(ObjectColor.WHITE)) {
            game.getState().processAction(game);
            if (permanent.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(permanent.getId())
                    && game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
                // A replacement effect has moved the card to another zone as grvayard
                return true;
            }
            Player permanentController = game.getPlayer(permanent.getControllerId());
            if (permanentController != null) {
                int damage = permanent.getPower().getValue();
                permanentController.damage(damage, source.getSourceId(), source, game);
            }
        }
        return false;
    }
}
