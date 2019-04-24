
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class TsabosDecree extends CardImpl {

    public TsabosDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{B}");

        // Choose a creature type. Target player reveals their hand and discards all creature cards of that type. Then destroy all creatures of that type that player controls. They can't be regenerated.
        this.getSpellAbility().addEffect(new TsabosDecreeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public TsabosDecree(final TsabosDecree card) {
        super(card);
    }

    @Override
    public TsabosDecree copy() {
        return new TsabosDecree(this);
    }
}

class TsabosDecreeEffect extends OneShotEffect {

    public TsabosDecreeEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Choose a creature type. Target player reveals their hand and discards all creature cards of that type. Then destroy all creatures of that type that player controls. They can't be regenerated";
    }

    public TsabosDecreeEffect(final TsabosDecreeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null) {
            Choice typeChoice = new ChoiceCreatureType(sourceObject);
            if (!player.choose(outcome, typeChoice, game)) {
                return false;
            }
            game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
            targetPlayer.revealCards("hand of " + targetPlayer.getName(), targetPlayer.getHand(), game);
            FilterCard filterCard = new FilterCard();
            filterCard.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
            List<Card> toDiscard = new ArrayList<>();
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if (filterCard.match(card, game)) {
                    toDiscard.add(card);
                }
            }
            for (Card card : toDiscard) {
                targetPlayer.discard(card, source, game);
            }
            FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
            filterCreaturePermanent.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
            for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
                if (creature.isControlledBy(targetPlayer.getId())) {
                    creature.destroy(source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TsabosDecreeEffect copy() {
        return new TsabosDecreeEffect(this);
    }
}
