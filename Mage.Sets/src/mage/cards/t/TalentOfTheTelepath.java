
package mage.cards.t;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class TalentOfTheTelepath extends CardImpl {

    public TalentOfTheTelepath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Target opponent reveals the top seven cards of their library. You may cast an instant or sorcery card from among them without paying its mana cost. Then that player puts the rest into their graveyard.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may cast up to two revealed instant and/or sorcery cards instead of one.
        getSpellAbility().addEffect(new TalentOfTheTelepathEffect());
        getSpellAbility().addTarget(new TargetOpponent());

    }

    public TalentOfTheTelepath(final TalentOfTheTelepath card) {
        super(card);
    }

    @Override
    public TalentOfTheTelepath copy() {
        return new TalentOfTheTelepath(this);
    }
}

class TalentOfTheTelepathEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public TalentOfTheTelepathEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent reveals the top seven cards of their library. You may cast an instant or sorcery card from among them without paying its mana cost. Then that player puts the rest into their graveyard. "
                + "<BR><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, you may cast up to two revealed instant and/or sorcery cards instead of one.";
    }

    public TalentOfTheTelepathEffect(final TalentOfTheTelepathEffect effect) {
        super(effect);
    }

    @Override
    public TalentOfTheTelepathEffect copy() {
        return new TalentOfTheTelepathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToCast = new CardsImpl();
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (targetOpponent != null && sourceObject != null) {
            Set<Card> allCards = targetOpponent.getLibrary().getTopCards(game, 7);
            Cards cards = new CardsImpl();
            for (Card card : allCards) {
                cards.add(card);
            }
            targetOpponent.revealCards(sourceObject.getIdName() + " - " + targetOpponent.getName() + "'s top library cards", cards, game);
            for (Card card : allCards) {
                if (filter.match(card, game)) {
                    cardsToCast.add(card);
                }
            }
            // cast an instant or sorcery for free
            if (!cardsToCast.isEmpty()) {
                int numberOfSpells = 1;
                if (SpellMasteryCondition.instance.apply(game, source)) {
                    numberOfSpells++;
                }
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {

                    TargetCard target = new TargetCard(Zone.LIBRARY, filter); // zone should be ignored here
                    target.setNotTarget(true);
                    while (numberOfSpells > 0
                            && !cardsToCast.isEmpty()
                            && controller.chooseUse(outcome, "Cast an instant or sorcery card from among them for free?", source, game)
                            && controller.choose(outcome, cardsToCast, target, game)) {
                        Card card = cardsToCast.get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                            numberOfSpells--;
                            cardsToCast.remove(card);
                            allCards.remove(card);
                        }
                        if (!controller.canRespond()) {
                            return false;
                        }
                        target.clearChosen();
                    }
                }
            }

            targetOpponent.moveCards(allCards, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}
