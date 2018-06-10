
package mage.cards.e;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken2;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class EzurisPredation extends CardImpl {

    public EzurisPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}{G}{G}");

        // For each creature your opponents control, create a 4/4 green Beast creature token. Each of those Beasts fights a different one of those creatures.
        this.getSpellAbility().addEffect(new EzurisPredationEffect());
    }

    public EzurisPredation(final EzurisPredation card) {
        super(card);
    }

    @Override
    public EzurisPredation copy() {
        return new EzurisPredation(this);
    }
}

class EzurisPredationEffect extends OneShotEffect {

    public EzurisPredationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature your opponents control, create a 4/4 green Beast creature token. Each of those Beasts fights a different one of those creatures";
    }

    public EzurisPredationEffect(final EzurisPredationEffect effect) {
        super(effect);
    }

    @Override
    public EzurisPredationEffect copy() {
        return new EzurisPredationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         * Players can't cast spells or activate any abilities in between the
         * Beasts entering the battlefield and fighting the other creatures.
         * Ifthe Beasts entering the battlefield cause any abilities to trigger,
         * those abilities will be put onto the stack after Ezuri's Predation is
         * finished resolving.
         * You choose which Beast is fighting which creature
         * an opponent controls. Each of the "fights" happens at the same time.
         * If Ezuri's Predation creates more than one token for any given
         * creature (due to an effect such as the one Doubling Season creates),
         * the extra tokens won't fight any creature.
         */
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCreaturePermanent filterCreature = new FilterCreaturePermanent();
            filterCreature.add(new ControllerPredicate(TargetController.OPPONENT));
            List<Permanent> creaturesOfOpponents = game.getBattlefield().getActivePermanents(filterCreature, source.getControllerId(), source.getSourceId(), game);
            if (!creaturesOfOpponents.isEmpty()) {
                CreateTokenEffect effect = new CreateTokenEffect(new BeastToken2(), creaturesOfOpponents.size());
                effect.apply(game, source);
                for (UUID tokenId : effect.getLastAddedTokenIds()) {
                    Permanent token = game.getPermanent(tokenId);
                    if (token != null) {
                        if (creaturesOfOpponents.isEmpty()) {
                            break;
                        }
                        Permanent opponentCreature = creaturesOfOpponents.iterator().next();
                        creaturesOfOpponents.remove(opponentCreature);
                        token.fight(opponentCreature, source, game);
                        game.informPlayers(token.getLogName() + " fights " + opponentCreature.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
