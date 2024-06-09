package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.command.Commander;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

public final class EncroachingMycosynth extends CardImpl {

    public EncroachingMycosynth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        // Nonland permanents you control are artifacts in addition to their other types. The same is true for permanent spells you control and nonland permanent cards you own that aren’t on the battlefield.
        this.addAbility(new SimpleStaticAbility(new EncroachingMycosynthEffect()));
    }

    private EncroachingMycosynth(final EncroachingMycosynth card) {
        super(card);
    }

    @Override
    public EncroachingMycosynth copy() {
        return new EncroachingMycosynth(this);
    }
}

class EncroachingMycosynthEffect extends ContinuousEffectImpl {

    EncroachingMycosynthEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Nonland permanents you control are artifacts in addition to their other types. " +
                "The same is true for permanent spells you control and nonland permanent cards you own that aren't on the battlefield";
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving); // March of the Machines
    }

    private EncroachingMycosynthEffect(final EncroachingMycosynthEffect effect) {
        super(effect);
    }

    @Override
    public EncroachingMycosynthEffect copy() {
        return new EncroachingMycosynthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Nonland permanent cards you own that aren't on the battlefield
        // in graveyard
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isPermanent(game) && !card.isLand(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // on Hand
        for (UUID cardId : controller.getHand()) {
            Card card = game.getCard(cardId);
            if (card != null && card.isPermanent(game) && !card.isLand(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // in Exile
        for (Card card : game.getState().getExile().getAllCards(game, source.getControllerId())) {
            if (card.isPermanent(game) && !card.isLand(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // in Library
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card.isOwnedBy(controller.getId()) && card.isPermanent(game) && !card.isLand(game) && !card.isArtifact(game)) {
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // commander in command zone
        for (CommandObject commandObject : game.getState().getCommand()) {
            if (commandObject instanceof Commander) {
                Card card = game.getCard((commandObject).getId());
                if (card != null && card.isOwnedBy(controller.getId())
                        && card.isPermanent(game) && !card.isLand(game) && !card.isArtifact(game)) {
                    card.addCardType(game, CardType.ARTIFACT);
                }
            }
        }

        // permanent spells you control
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell
                    && stackObject.isControlledBy(source.getControllerId())
                    && stackObject.isPermanent(game)
                    && !stackObject.isLand(game)
                    && !stackObject.isArtifact(game)) {
                Card card = ((Spell) stackObject).getCard();
                card.addCardType(game, CardType.ARTIFACT);
            }
        }
        // nonland permanents you control
        FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents you control");
        filter.add(new ControllerIdPredicate(source.getControllerId()));
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(
                filter, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.addCardType(game, CardType.ARTIFACT);
            }
        }
        return true;

    }
}
