package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.SeedGuardianToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DanceOfTheTumbleweeds extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic land card or a Desert card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ), SubType.DESERT.getPredicate()
        ));
    }

    public DanceOfTheTumbleweeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Search your library for a basic land card or a Desert card, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter)
        ));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {3} -- Create an X/X green Elemental creature token, where X is the number of lands you control.
        this.getSpellAbility().addMode(new Mode(new DanceOfTheTumbleweedsEffect()).withCost(new GenericManaCost(3)));
        this.getSpellAbility().addHint(LandsYouControlHint.instance);
    }

    private DanceOfTheTumbleweeds(final DanceOfTheTumbleweeds card) {
        super(card);
    }

    @Override
    public DanceOfTheTumbleweeds copy() {
        return new DanceOfTheTumbleweeds(this);
    }
}

class DanceOfTheTumbleweedsEffect extends OneShotEffect {

    DanceOfTheTumbleweedsEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X green Elemental creature token, where X is the number of lands you control";
    }

    private DanceOfTheTumbleweedsEffect(final DanceOfTheTumbleweedsEffect effect) {
        super(effect);
    }

    @Override
    public DanceOfTheTumbleweedsEffect copy() {
        return new DanceOfTheTumbleweedsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new SeedGuardianToken(LandsYouControlCount.instance.calculate(game, source, this))
                .putOntoBattlefield(1, game, source);
    }
}
