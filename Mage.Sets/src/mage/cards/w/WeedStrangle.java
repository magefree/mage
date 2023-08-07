
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WeedStrangle extends CardImpl {

    public WeedStrangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Destroy target creature. Clash with an opponent. If you win, you gain life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new WeedStrangleEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private WeedStrangle(final WeedStrangle card) {
        super(card);
    }

    @Override
    public WeedStrangle copy() {
        return new WeedStrangle(this);
    }
}

class WeedStrangleEffect extends OneShotEffect {

    public WeedStrangleEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature. Clash with an opponent. If you win, you gain life equal to that creature's toughness";
    }

    public WeedStrangleEffect(final WeedStrangleEffect effect) {
        super(effect);
    }

    @Override
    public WeedStrangleEffect copy() {
        return new WeedStrangleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && creature != null) {
            creature.destroy(source, game, false);
            if (new ClashEffect().apply(game, source)) {
                controller.gainLife(creature.getToughness().getValue(), game, source);
            }
            return true;
        }
        return false;
    }
}
