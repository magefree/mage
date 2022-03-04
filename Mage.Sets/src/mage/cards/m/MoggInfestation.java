package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class MoggInfestation extends CardImpl {

    public MoggInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control.
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new MoggInfestationEffect());

    }

    private MoggInfestation(final MoggInfestation card) {
        super(card);
    }

    @Override
    public MoggInfestation copy() {
        return new MoggInfestation(this);
    }
}

class MoggInfestationEffect extends OneShotEffect {

    public MoggInfestationEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures target player controls. For each creature that died this way, create two 1/1 red Goblin creature tokens under that player's control";
    }

    public MoggInfestationEffect(final MoggInfestationEffect effect) {
        super(effect);
    }

    @Override
    public MoggInfestationEffect copy() {
        return new MoggInfestationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards creaturesDied = new CardsImpl();
        if (controller != null
                && getTargetPointer().getFirst(game, source) != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                if (permanent.destroy(source, game, false)) {
                    if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) { // If a commander is replaced to command zone, the creature does not die
                        creaturesDied.add(permanent);
                    }
                }
            }
            game.getState().processAction(game);  // Bug #8548
            if (creaturesDied.isEmpty()) {
                return true;
            }
            for (Card c : creaturesDied.getCards(game)) {
                if (game.getState().getZone(c.getId()) == Zone.GRAVEYARD) {
                    Effect effect = new CreateTokenTargetEffect(new GoblinToken(), 2);
                    effect.setTargetPointer(getTargetPointer());
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
