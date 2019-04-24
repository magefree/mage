package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtherWell extends CardImpl {

    public EtherWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Put target creature on top of its owner's library. If that creature is red, you may put it on the bottom of its owner's library instead.
        this.getSpellAbility().addEffect(new EtherWellEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EtherWell(final EtherWell card) {
        super(card);
    }

    @Override
    public EtherWell copy() {
        return new EtherWell(this);
    }
}

class EtherWellEffect extends OneShotEffect {

    EtherWellEffect() {
        super(Outcome.Benefit);
        staticText = "Put target creature on top of its owner's library. " +
                "If that creature is red, you may put it on the bottom of its owner's library instead.";
    }

    private EtherWellEffect(final EtherWellEffect effect) {
        super(effect);
    }

    @Override
    public EtherWellEffect copy() {
        return new EtherWellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        if (permanent.getColor(game).isRed()
                && player.chooseUse(outcome, "Put " + permanent.getLogName() +
                " on the bottom of its owner's library?", source, game
        )) {
            player.putCardsOnBottomOfLibrary(permanent, game, source, true);
            return true;
        }
        player.putCardsOnTopOfLibrary(new CardsImpl(permanent), game, source, true);
        return true;
    }
}