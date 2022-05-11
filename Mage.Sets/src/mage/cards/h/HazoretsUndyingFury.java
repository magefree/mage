package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class HazoretsUndyingFury extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("Lands you control");

    public HazoretsUndyingFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        //Shuffle your library, then exile the top four cards.
        //You may cast any number of nonland cards with converted mana
        //cost 5 or less from among them without paying their mana costs.
        this.getSpellAbility().addEffect(new HazoretsUndyingFuryEffect());

        //Land you control don't untap during your next untap step.
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU,
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND
        ).setText("Lands you control don't untap during your next untap phase"));
    }

    private HazoretsUndyingFury(final HazoretsUndyingFury card) {
        super(card);
    }

    @Override
    public HazoretsUndyingFury copy() {
        return new HazoretsUndyingFury(this);
    }
}

class HazoretsUndyingFuryEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public HazoretsUndyingFuryEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Shuffle your library, then exile the top four cards. " +
                "You may cast any number of spells with mana value " +
                "5 or less from among them without paying their mana costs";
    }

    public HazoretsUndyingFuryEffect(final HazoretsUndyingFuryEffect effect) {
        super(effect);
    }

    @Override
    public HazoretsUndyingFuryEffect copy() {
        return new HazoretsUndyingFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.shuffleLibrary(source, game);
        // move cards from library to exile
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        controller.moveCards(cards, Zone.EXILED, source, game);
        // cast the possible cards without paying the mana
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, filter);
        return true;
    }
}
