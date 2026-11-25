package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class SeventyThousandLightYearsFromHome extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Spacecraft");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), SubType.SPACECRAFT.getPredicate()));
    }

    public SeventyThousandLightYearsFromHome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target creature or Spacecraft. Its controller may search their library for a basic land card, reveal it, put it into their hand, then shuffle.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new SeventyThousandLightYearsFromHomeEffect());
    }

    private SeventyThousandLightYearsFromHome(final SeventyThousandLightYearsFromHome card) {
        super(card);
    }

    @Override
    public SeventyThousandLightYearsFromHome copy() {
        return new SeventyThousandLightYearsFromHome(this);
    }
}

class SeventyThousandLightYearsFromHomeEffect extends SearchEffect {

    SeventyThousandLightYearsFromHomeEffect() {
        super(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), Outcome.DrawCard);
        staticText = "its controller may search their library for a basic land card, reveal it, "
            + "put it into their hand, then shuffle";
    }

    private SeventyThousandLightYearsFromHomeEffect(final SeventyThousandLightYearsFromHomeEffect effect) {
        super(effect);
    }

    @Override
    public SeventyThousandLightYearsFromHomeEffect copy() {
        return new SeventyThousandLightYearsFromHomeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = getTargetPointer().getControllerOfFirstTargetOrLKI(game, source);
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Search your library for a basic land card?", source, game)) {
            return true;
        }
        target.clearChosen();
        if (player.searchLibrary(target, source, game)) {
            Cards cards = new CardsImpl(target.getTargets());
            player.revealCards(source, cards, game);
            player.moveCards(cards, Zone.HAND, source, game);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
