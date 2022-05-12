package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VillainousWealth extends CardImpl {

    public VillainousWealth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{G}{U}");

        // Target opponent exiles the top X cards of their library. You may cast any number of nonland cards 
        // with converted mana cost X or less from among them without paying their mana cost.
        this.getSpellAbility().addEffect(new VillainousWealthEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private VillainousWealth(final VillainousWealth card) {
        super(card);
    }

    @Override
    public VillainousWealth copy() {
        return new VillainousWealth(this);
    }
}

class VillainousWealthEffect extends OneShotEffect {

    public VillainousWealthEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Target opponent exiles the top X cards of their library. "
                + "You may cast any number of spells with mana value X "
                + "or less from among them without paying their mana costs";
    }

    public VillainousWealthEffect(final VillainousWealthEffect effect) {
        super(effect);
    }

    @Override
    public VillainousWealthEffect copy() {
        return new VillainousWealthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        int xValue = source.getManaCostsToPay().getX();
        if (controller == null || opponent == null || xValue < 1) {
            return false;
        }
        Cards cards = new CardsImpl(opponent.getLibrary().getTopCards(game, xValue));
        opponent.moveCards(cards, Zone.EXILED, source, game);
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, filter);
        return true;
    }
}
