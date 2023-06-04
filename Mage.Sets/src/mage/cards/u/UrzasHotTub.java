package mage.cards.u;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author L_J
 */
public class UrzasHotTub extends CardImpl {

    public UrzasHotTub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, Discard a card: Search your library for a card that shares a complete word in its name with the discarded card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasHotTubEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }

    private UrzasHotTub(final UrzasHotTub card) {
        super(card);
    }

    @Override
    public UrzasHotTub copy() {
        return new UrzasHotTub(this);
    }
}

class UrzasHotTubEffect extends OneShotEffect {

    public UrzasHotTubEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Search your library for a card that shares a complete word in its name with the discarded card, reveal it, put it into your hand, then shuffle";
    }

    public UrzasHotTubEffect(final UrzasHotTubEffect effect) {
        super(effect);
    }

    @Override
    public UrzasHotTubEffect copy() {
        return new UrzasHotTubEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof DiscardTargetCost) {
                DiscardTargetCost discardCost = (DiscardTargetCost) cost;
                Card discardedCard = discardCost.getCards().get(0);
                if (discardedCard != null) {
                    FilterCard filter = new FilterCard();
                    filter.add(new UrzasHotTubPredicate(discardedCard.getName()));
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true).apply(game, source);
                }
            }
        }
        return false;
    }
}

class UrzasHotTubPredicate implements Predicate<MageObject> {

    private final String referenceName;

    public UrzasHotTubPredicate(String referenceName) {
        this.referenceName = referenceName;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        String name = input.getName();
        if (input instanceof SplitCard) {
            return sharesWordWithName(((SplitCard) input).getLeftHalfCard().getName()) || sharesWordWithName(((SplitCard) input).getRightHalfCard().getName());
        } else if (input instanceof ModalDoubleFacedCard) {
            return sharesWordWithName(((ModalDoubleFacedCard) input).getLeftHalfCard().getName()) || sharesWordWithName(((ModalDoubleFacedCard) input).getRightHalfCard().getName());
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            SplitCard card = (SplitCard) ((Spell) input).getCard();
            return sharesWordWithName(card.getLeftHalfCard().getName()) || sharesWordWithName(card.getRightHalfCard().getName());
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4);
                return sharesWordWithName(leftName) || sharesWordWithName(rightName);
            } else {
                return sharesWordWithName(name);
            }
        }
    }

    private boolean sharesWordWithName(String str) {
        if (referenceName == null || referenceName == "") {
            return false;
        }
        String[] arr = referenceName.split("\\s+");
        for (int i = 0; i < arr.length; i++) {
            if (str.contains(arr[i].replaceAll(",", ""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
