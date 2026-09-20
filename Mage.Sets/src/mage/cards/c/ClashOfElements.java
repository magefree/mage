package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class ClashOfElements extends CardImpl {

    public ClashOfElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Choose target nonland permanent. Its owner may put it on the top of their library. If they do, Clash of Elements deals 2 damage to them. If they didn't put the card on top of their library, they put it on the bottom.
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new ClashOfElementsEffect());
    }

    private ClashOfElements(final ClashOfElements card) {
        super(card);
    }

    @Override
    public ClashOfElements copy() {
        return new ClashOfElements(this);
    }
}

class ClashOfElementsEffect extends OneShotEffect {

    ClashOfElementsEffect() {
        super(Outcome.Removal);
        staticText = "choose target nonland permanent. Its owner may put it on the top of their library. "
                + "If they do, {this} deals 2 damage to them. If they didn't put the card on top of their library, they put it on the bottom";
    }

    private ClashOfElementsEffect(final ClashOfElementsEffect effect) {
        super(effect);
    }

    @Override
    public ClashOfElementsEffect copy() {
        return new ClashOfElementsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player owner = game.getPlayer(permanent.getOwnerId());
        if (owner == null) {
            return false;
        }
        if (owner.chooseUse(
            Outcome.Neutral,
            "Put " + permanent.getIdName() + " on the top of your library and take 2 damage? (Otherwise on the bottom)",
            null, "Top", "Bottom", source, game
        )) {
            owner.putCardsOnTopOfLibrary(permanent, game, source, false);
            owner.damage(2, source.getSourceId(), source, game);
        } else {
            owner.putCardsOnBottomOfLibrary(permanent, game, source);
        }
        return true;
    }
}
