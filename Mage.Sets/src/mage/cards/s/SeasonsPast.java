
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SeasonsPast extends CardImpl {

    public SeasonsPast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Return any number of cards with different converted mana costs from your graveyard to your hand. Put Seasons Past on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new SeasonsPastEffect());
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    private SeasonsPast(final SeasonsPast card) {
        super(card);
    }

    @Override
    public SeasonsPast copy() {
        return new SeasonsPast(this);
    }
}

class SeasonsPastEffect extends OneShotEffect {

    public SeasonsPastEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return any number of cards with different mana values from your graveyard to your hand";
    }

    public SeasonsPastEffect(final SeasonsPastEffect effect) {
        super(effect);
    }

    @Override
    public SeasonsPastEffect copy() {
        return new SeasonsPastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SeasonsPastTarget target = new SeasonsPastTarget();
            controller.choose(outcome, target, source, game);
            controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

class SeasonsPastTarget extends TargetCardInYourGraveyard {

    public SeasonsPastTarget() {
        super(0, Integer.MAX_VALUE, new FilterCard("cards with different mana values from your graveyard"));
    }

    public SeasonsPastTarget(SeasonsPastTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<Integer> usedCMC = new HashSet<>();
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                usedCMC.add(card.getManaValue());
            }
        }
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> leftPossibleTargets = new HashSet<>();
        for (UUID targetId : possibleTargets) {
            Card card = game.getCard(targetId);
            if (card != null && !usedCMC.contains(card.getManaValue())) {
                leftPossibleTargets.add(targetId);
            }
        }
        return leftPossibleTargets;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        if (super.canTarget(playerId, objectId, source, game)) {
            Set<Integer> usedCMC = new HashSet<>();
            for (UUID targetId : this.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    usedCMC.add(card.getManaValue());
                }
            }
            Card card = game.getCard(objectId);
            return card != null && !usedCMC.contains(card.getManaValue());
        }
        return false;
    }

    @Override
    public SeasonsPastTarget copy() {
        return new SeasonsPastTarget(this);
    }

}
