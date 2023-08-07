package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Boar2Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.permanent.PermanentToken;

/**
 * @author LevelX2
 */
public final class CurseOfTheSwine extends CardImpl {

    public CurseOfTheSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Exile X target creatures. For each creature exiled this way, its controller creates a 2/2 green Boar creature token.
        this.getSpellAbility().addEffect(new CurseOfTheSwineEffect());

        // Correct number of targets will be set in adjustTargets
        this.getSpellAbility().setTargetAdjuster(CurseOfTheSwineAdjuster.instance);

    }

    private CurseOfTheSwine(final CurseOfTheSwine card) {
        super(card);
    }

    @Override
    public CurseOfTheSwine copy() {
        return new CurseOfTheSwine(this);
    }
}

enum CurseOfTheSwineAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

class CurseOfTheSwineEffect extends OneShotEffect {

    public CurseOfTheSwineEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile X target creatures. For each creature exiled this way, "
                + "its controller creates a 2/2 green Boar creature token";
    }

    public CurseOfTheSwineEffect(final CurseOfTheSwineEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheSwineEffect copy() {
        return new CurseOfTheSwineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards creaturesToExile = new CardsImpl();
        if (controller != null) {
            Map<UUID, Integer> playersWithTargets = new HashMap<>();
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                // handle creature tokens here due to LKI being non-existent for them
                // TODO implement a way to verify that tokens are indeed exiled from the battlefield
                if (creature instanceof PermanentToken) {
                    playersWithTargets.put(creature.getControllerId(),
                            playersWithTargets.getOrDefault(creature.getControllerId(), 0) + 1);
                }
                if (creature != null) {
                    creaturesToExile.add(creature);
                }
            }

            // move creatures to exile all at once
            controller.moveCards(creaturesToExile, Zone.EXILED, source, game);

            // Count all creatures actually exiled and add them to the player's count
            for (Card card : creaturesToExile.getCards(game)) {
                Permanent lkiP = game.getPermanentOrLKIBattlefield(card.getId());
                // note that tokens have no LKI once they are moved from the battlefield so they are handled earlier
                if (lkiP != null
                        && game.getState().getZone(lkiP.getId()) == Zone.EXILED) {
                    playersWithTargets.put(lkiP.getControllerId(),
                            playersWithTargets.getOrDefault(lkiP.getControllerId(), 0) + 1);
                }
            }
            game.getState().processAction(game);
            Boar2Token swineToken = new Boar2Token();
            for (Map.Entry<UUID, Integer> exiledByController : playersWithTargets.entrySet()) {
                swineToken.putOntoBattlefield(exiledByController.getValue(), game, source, exiledByController.getKey());
            }
            return true;
        }
        return false;
    }
}
