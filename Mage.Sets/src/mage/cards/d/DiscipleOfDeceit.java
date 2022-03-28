
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class DiscipleOfDeceit extends CardImpl {

    public DiscipleOfDeceit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Inspired - Whenever Disciple of Deceit becomes untapped, you may discard a nonland card. If you do, search your library for a card with the same converted mana cost as that card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new InspiredAbility(new DiscipleOfDeceitEffect(), false));
        
    }

    private DiscipleOfDeceit(final DiscipleOfDeceit card) {
        super(card);
    }

    @Override
    public DiscipleOfDeceit copy() {
        return new DiscipleOfDeceit(this);
    }
}

class DiscipleOfDeceitEffect extends OneShotEffect {
    
    public DiscipleOfDeceitEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may discard a nonland card. If you do, search your library for a card with the same mana value as that card, reveal it, put it into your hand, then shuffle";
    }
    
    public DiscipleOfDeceitEffect(final DiscipleOfDeceitEffect effect) {
        super(effect);
    }
    
    @Override
    public DiscipleOfDeceitEffect copy() {
        return new DiscipleOfDeceitEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (player != null && mageObject != null) {
            Cost cost = new DiscardTargetCost(new TargetCardInHand(new FilterNonlandCard()));
            String message = "Discard a nonland card to search your library?";
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && player.chooseUse(Outcome.Detriment, message, source, game)) {
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    Card card = game.getCard(cost.getTargets().getFirstTarget());
                    if (card == null) {
                        return false;
                    }
                    String targetName = "card with mana value of " + card.getManaValue();
                    FilterCard filter = new FilterCard(targetName);
                    filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, card.getManaValue()));
                    return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true).apply(game, source);                    
                }
            }
            return true;
        }
        return false;
    }
}
