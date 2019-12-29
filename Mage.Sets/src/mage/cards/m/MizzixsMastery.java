package mage.cards.m;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MizzixsMastery extends CardImpl {

    public MizzixsMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Exile target card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile Mizzix's Mastery.
        this.getSpellAbility().addEffect(new MizzixsMasteryEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterInstantOrSorceryCard("card that's an instant or sorcery from your graveyard")));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());

        // Overload {5}{R}{R}{R}
        Ability ability = new OverloadAbility(this, new MizzixsMasteryOverloadEffect(), new ManaCostsImpl("{5}{R}{R}{R}"));
        ability.addEffect(ExileSpellEffect.getInstance());
        this.addAbility(ability);
    }

    public MizzixsMastery(final MizzixsMastery card) {
        super(card);
    }

    @Override
    public MizzixsMastery copy() {
        return new MizzixsMastery(this);
    }
}

class MizzixsMasteryEffect extends OneShotEffect {

    public MizzixsMasteryEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile target card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost";
    }

    public MizzixsMasteryEffect(final MizzixsMasteryEffect effect) {
        super(effect);
    }

    @Override
    public MizzixsMasteryEffect copy() {
        return new MizzixsMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                if (controller.moveCards(card, Zone.EXILED, source, game)) {
                    Card cardCopy = game.copyCard(card, source, source.getControllerId());
                    if (cardCopy.getSpellAbility().canChooseTarget(game)
                            && controller.chooseUse(outcome, "Cast copy of " + card.getName() + " without paying its mana cost?", source, game)) {
                        controller.cast(cardCopy.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class MizzixsMasteryOverloadEffect extends OneShotEffect {

    public MizzixsMasteryOverloadEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile each card that's an instant or sorcery from your graveyard. For each card exiled this way, copy it, and you may cast the copy without paying its mana cost. Exile {this}";
    }

    public MizzixsMasteryOverloadEffect(final MizzixsMasteryOverloadEffect effect) {
        super(effect);
    }

    @Override
    public MizzixsMasteryOverloadEffect copy() {
        return new MizzixsMasteryOverloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = controller.getGraveyard().getCards(new FilterInstantOrSorceryCard(), source.getId(), source.getControllerId(), game);
            if (!cardsToExile.isEmpty()) {
                if (controller.moveCards(cardsToExile, Zone.EXILED, source, game)) {
                    Cards copiedCards = new CardsImpl();
                    for (Card card : cardsToExile) {
                        copiedCards.add(game.copyCard(card, source, source.getControllerId()));
                    }
                    boolean continueCasting = true;
                    while (continueCasting && controller.isInGame()) {
                        TargetCard targetCard = new TargetCard(0, 1, Zone.EXILED, new FilterCard("copied card to cast without paying its mana cost?"));
                        targetCard.setNotTarget(true);
                        if (controller.choose(outcome, copiedCards, targetCard, game)) {
                            Card selectedCard = game.getCard(targetCard.getFirstTarget());
                            if (selectedCard != null && selectedCard.getSpellAbility().canChooseTarget(game)) {
                                if (controller.cast(selectedCard.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game))) {
                                    copiedCards.remove(selectedCard);
                                }
                            }
                        }
                        continueCasting = !copiedCards.isEmpty()
                                && controller.chooseUse(outcome, "Cast one of the copied cards without paying its mana cost?", source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
