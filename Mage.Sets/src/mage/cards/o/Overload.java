
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public final class Overload extends CardImpl {

    public Overload(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Destroy target artifact if its converted mana cost is 2 or less. If Overload was kicked, destroy that artifact if its converted mana cost is 5 or less instead.
        this.getSpellAbility().addEffect(new OverloadEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    private Overload(final Overload card) {
        super(card);
    }

    @Override
    public Overload copy() {
        return new Overload(this);
    }
}

class OverloadEffect extends OneShotEffect {

    OverloadEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact if its mana value is 2 or less. If this spell was kicked, destroy that artifact if its mana value is 5 or less instead.";
    }

    OverloadEffect(final OverloadEffect effect) {
        super(effect);
    }

    @Override
    public OverloadEffect copy() {
        return new OverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetArtifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (targetArtifact != null) {
                int cmc = targetArtifact.getManaValue();
                if (cmc <= 2 || (KickedCondition.ONCE.apply(game, source) && cmc <= 5)) {
                    targetArtifact.destroy(source, game, false);
                }
            }
            return true;
        }
        return false;
    }
}
