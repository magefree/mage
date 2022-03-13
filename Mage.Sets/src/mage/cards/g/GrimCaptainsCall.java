
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class GrimCaptainsCall extends CardImpl {

    public GrimCaptainsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return a Pirate card from your graveyard to your hand, then do the same for Vampire, Dinosaur, and Merfolk.
        this.getSpellAbility().addEffect(new GrimCaptainsCallEffect());
    }

    private GrimCaptainsCall(final GrimCaptainsCall card) {
        super(card);
    }

    @Override
    public GrimCaptainsCall copy() {
        return new GrimCaptainsCall(this);
    }
}

class GrimCaptainsCallEffect extends OneShotEffect {

    public GrimCaptainsCallEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return a Pirate card from your graveyard to your hand, then do the same for Vampire, Dinosaur, and Merfolk";
    }

    public GrimCaptainsCallEffect(final GrimCaptainsCallEffect effect) {
        super(effect);
    }

    @Override
    public GrimCaptainsCallEffect copy() {
        return new GrimCaptainsCallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            returnToHand(game, SubType.PIRATE, controller, source);
            returnToHand(game, SubType.VAMPIRE, controller, source);
            returnToHand(game, SubType.DINOSAUR, controller, source);
            returnToHand(game, SubType.MERFOLK, controller, source);
            return true;
        }
        return false;
    }

    private void returnToHand(Game game, SubType subType, Player controller, Ability source) {
        FilterCreatureCard filter = new FilterCreatureCard(subType.getDescription() + " card");
        filter.add(subType.getPredicate());
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterCreatureCard(filter));
        if (target.canChoose(source.getControllerId(), source, game)) {
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
        }
    }
}
