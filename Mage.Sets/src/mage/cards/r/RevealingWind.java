
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RevealingWind extends CardImpl {

    public RevealingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Prevent all combat damage that would be dealt this turn. You may look at each face-down creature that's attacking or blocking.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addEffect(new RevealingWindEffect());
    }

    private RevealingWind(final RevealingWind card) {
        super(card);
    }

    @Override
    public RevealingWind copy() {
        return new RevealingWind(this);
    }
}

class RevealingWindEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterAttackingOrBlockingCreature("face-down creature that's attacking or blocking");

    static {
        filter.add(FaceDownPredicate.instance);
    }

    public RevealingWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may look at each face-down creature that's attacking or blocking";
    }

    public RevealingWindEffect(final RevealingWindEffect effect) {
        super(effect);
    }

    @Override
    public RevealingWindEffect copy() {
        return new RevealingWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            while (game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0 &&
                    controller.chooseUse(outcome, "Look at a face-down attacking creature?", source, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
                Target target = new TargetCreaturePermanent(filter);
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        Cards cards = new CardsImpl(card);
                        controller.lookAtCards(sourceObject.getName(), cards, game);
                        game.informPlayers(controller.getLogName() + " look at a face-down attacking creature");
                    }
                }
            }
        }        
        return true;
    }
}
