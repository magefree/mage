package mage.cards.e;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Ketsuban
 */
public final class EverythingamajigE extends CardImpl {

    public EverythingamajigE(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Zuran Orb
        // Sacrifice a land: You gain 2 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(2), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT))));

        // Ashnod's Altar
        // Sacrifice a creature: Add {C}{C} to your mana pool.
        SacrificeTargetCost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.ColorlessMana(2), CreaturesYouControlCount.instance),
                cost));

        // Urza's Hot Tub
        // 2, Discard a card: Search your library for a card that shares a complete word in its name with the name of the discarded card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UrzasHotTubEffect(), new GenericManaCost(2));
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }

    private EverythingamajigE(final EverythingamajigE card) {
        super(card);
    }

    @Override
    public EverythingamajigE copy() {
        return new EverythingamajigE(this);
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
        if (referenceName == null || referenceName.equals("")) {
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
