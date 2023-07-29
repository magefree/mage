package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardWithLessMVPutIntoPlay;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author antoni-g
 */
public final class FinaleOfDevastation extends CardImpl {

    public FinaleOfDevastation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");
        // Search your library and/or graveyard for a creature card with converted mana cost X or less and put it onto the battlefield. If you search your library this way, shuffle it. 
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardWithLessMVPutIntoPlay(StaticFilters.FILTER_CARD_CREATURE));
        // If X is 10 or more, creatures you control get +X/+X and gain haste until end of turn.
        this.getSpellAbility().addEffect(new FinaleOfDevastationEffect());
    }

    private FinaleOfDevastation(final FinaleOfDevastation card) {
        super(card);
    }

    @Override
    public FinaleOfDevastation copy() {
        return new FinaleOfDevastation(this);
    }
}

class FinaleOfDevastationEffect extends OneShotEffect {

    FinaleOfDevastationEffect() {
        super(Outcome.Benefit);
        staticText = "If X is 10 or more, creatures you control get +X/+X and gain haste until end of turn.";
    }

    private FinaleOfDevastationEffect(final FinaleOfDevastationEffect effect) {
        super(effect);
    }

    @Override
    public FinaleOfDevastationEffect copy() {
        return new FinaleOfDevastationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue >= 10) {
            ContinuousEffect effect1 = new BoostControlledEffect(xValue, xValue, Duration.EndOfTurn);
            game.addEffect(effect1, source);
            ContinuousEffect effect2 = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
            game.addEffect(effect2, source);
        }
        return true;
    }
}
