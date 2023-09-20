
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class EndHostilities extends CardImpl {

    public EndHostilities(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        // Destroy all creatures and all permanents attached to creatures.
        this.getSpellAbility().addEffect(new EndHostilitiesEffect());
    }

    private EndHostilities(final EndHostilities card) {
        super(card);
    }

    @Override
    public EndHostilities copy() {
        return new EndHostilities(this);
    }
}

class EndHostilitiesEffect extends OneShotEffect {

    public EndHostilitiesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures and all permanents attached to creatures.";
    }

    private EndHostilitiesEffect(final EndHostilitiesEffect effect) {
        super(effect);
    }

    @Override
    public EndHostilitiesEffect copy() {
        return new EndHostilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toDestroy = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(controller.getId(), game)) {
                if (permanent.isCreature(game)) {
                    toDestroy.add(permanent);
                } else if (permanent.getAttachedTo() != null) {
                    Permanent attachedTo = game.getPermanent(permanent.getAttachedTo());
                    if (attachedTo != null && attachedTo.isCreature(game)) {
                        toDestroy.add(permanent);                        
                    }
                }
            }
            for (Permanent permanent : toDestroy){
                permanent.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }
}
