package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;
import mage.ApprovingObject;

/**
 * @author LevelX2
 */
public final class TalentOfTheTelepath extends CardImpl {

    public TalentOfTheTelepath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Target opponent reveals the top seven cards of their library. 
        // You may cast an instant or sorcery card from among them without paying 
        // its mana cost. Then that player puts the rest into their graveyard.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or 
        // sorcery cards in your graveyard, you may cast up to two revealed instant 
        // and/or sorcery cards instead of one.
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
        this.staticText = "Target opponent reveals the top seven cards of their "
                + "library. You may cast an instant or sorcery card from among them "
                + "without paying its mana cost. Then that player puts the rest into their graveyard. "
                + "<BR><i>Spell mastery</i> &mdash; If there are two or more instant "
                + "and/or sorcery cards in your graveyard, you may cast up to two "
                + "revealed instant and/or sorcery cards instead of one.";
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
            Cards cards = new CardsImpl(allCards);
            targetOpponent.revealCards(sourceObject.getIdName() + " - "
                    + targetOpponent.getName() + "'s top library cards", cards, game);
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
                    while (controller.canRespond()
                            && numberOfSpells > 0
                            && !cardsToCast.isEmpty()
                            && controller.chooseUse(outcome, "Cast an instant or sorcery card "
                            + "from among them for free?", source, game)
                            && controller.choose(Outcome.PlayForFree, cardsToCast, target, game)) {
                        Card card = cardsToCast.get(target.getFirstTarget(), game);
                        if (card != null) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                            Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                            cardsToCast.remove(card);
                            if (cardWasCast) {
                                numberOfSpells--;
                                allCards.remove(card);
                            }
                        } else {
                            break;
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
