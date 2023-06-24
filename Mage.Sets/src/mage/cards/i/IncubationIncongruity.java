package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FrogLizardToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IncubationIncongruity extends SplitCard {

    public IncubationIncongruity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.INSTANT}, "{G/U}", "{1}{G}{U}", SpellAbilityType.SPLIT);

        // Incubation
        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM));

        // Incongruity
        // Exile target creature. That creature's controller creates a 3/3 green Frog Lizard creature token.
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new IncongruityEffect());
    }

    private IncubationIncongruity(final IncubationIncongruity card) {
        super(card);
    }

    @Override
    public IncubationIncongruity copy() {
        return new IncubationIncongruity(this);
    }
}

class IncongruityEffect extends OneShotEffect {

    public IncongruityEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "That creature's controller creates a 3/3 green Frog Lizard creature token";
    }

    public IncongruityEffect(final IncongruityEffect effect) {
        super(effect);
    }

    @Override
    public IncongruityEffect copy() {
        return new IncongruityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // If the target creature is an illegal target by the time Incongruity tries to resolve, the spell doesn’t resolve.
        // No player creates a Frog Lizard token.
        // (2019-01-25)
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source); // must use LKI
        if (permanent != null) {
            FrogLizardToken token = new FrogLizardToken();
            token.putOntoBattlefield(1, game, source, permanent.getControllerId());
        }
        return true;
    }
}
