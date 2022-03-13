
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class PerilousResearch extends CardImpl {

    public PerilousResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Draw two cards, then sacrifice a permanent.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new PerilousResearchEffect());
    }

    private PerilousResearch(final PerilousResearch card) {
        super(card);
    }

    @Override
    public PerilousResearch copy() {
        return new PerilousResearch(this);
    }
}

class PerilousResearchEffect extends OneShotEffect {

    public PerilousResearchEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "then sacrifice a permanent";
    }

    public PerilousResearchEffect(final PerilousResearchEffect effect) {
        super(effect);
    }

    @Override
    public PerilousResearchEffect copy() {
        return new PerilousResearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetControlledPermanent();

            if (target.canChoose(player.getId(), source, game) && player.choose(Outcome.Sacrifice, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    return permanent.sacrifice(source, game);
                }
            }
        }
        return false;
    }
}
