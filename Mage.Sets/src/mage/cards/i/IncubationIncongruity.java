package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
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
        // Look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getLeftHalfCard().getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                new StaticValue(5), false,
                new StaticValue(1), StaticFilters.FILTER_CARD_CREATURE_A,
                Zone.LIBRARY, false, true, false,
                Zone.HAND, false, false, false
        ).setBackInRandomOrder(true));

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
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (permanent != null) {
            FrogLizardToken token = new FrogLizardToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), permanent.getControllerId());
        }
        return true;
    }
}
