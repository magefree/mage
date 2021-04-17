package mage.cards.f;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.constants.CardType;
import mage.abilities.effects.common.search.SearchLibraryGraveyardWithLessMVPutIntoPlay;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;


/**
 *
 * @author antoni-g
 */
public final class FinaleOfDevastation extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature");
    
    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public FinaleOfDevastation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");
        // Search your library and/or graveyard for a creature card with converted mana cost X or less and put it onto the battlefield. If you search your library this way, shuffle it. 
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardWithLessMVPutIntoPlay(filter));
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