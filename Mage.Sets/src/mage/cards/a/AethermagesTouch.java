
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class AethermagesTouch extends CardImpl {

    public AethermagesTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");

        // Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains "At the beginning of your end step, return this creature to its owner's hand." Then put the rest of the cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new AethermagesTouchEffect());
    }

    private AethermagesTouch(final AethermagesTouch card) {
        super(card);
    }

    @Override
    public AethermagesTouch copy() {
        return new AethermagesTouch(this);
    }
}

class AethermagesTouchEffect extends OneShotEffect {

    public AethermagesTouchEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains \"At the beginning of your end step, return this creature to its owner's hand.\" Then put the rest of the cards revealed this way on the bottom of your library in any order";
    }

    public AethermagesTouchEffect(final AethermagesTouchEffect effect) {
        super(effect);
    }

    @Override
    public AethermagesTouchEffect copy() {
        return new AethermagesTouchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
            if (!cards.isEmpty()) {
                FilterCreatureCard filter = new FilterCreatureCard("a creature card to put onto the battlefield");
                controller.revealCards(sourceObject.getIdName(), cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filter);
                if (cards.count(filter, game) > 0 && controller.choose(outcome, cards, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                            // It gains \"At the beginning of your end step, return this creature to its owner's hand.\"
                            Permanent permanent = game.getPermanent(card.getId());
                            if (permanent != null) {
                                Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), TargetController.YOU, null, false);
                                ContinuousEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
                                effect.setTargetPointer(new FixedTarget(permanent, game));
                                game.addEffect(effect, source);
                            }
                        }
                    }

                }
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);

            }
            return true;
        }
        return false;
    }
}
