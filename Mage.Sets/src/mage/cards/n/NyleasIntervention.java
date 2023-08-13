package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyleasIntervention extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public NyleasIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Choose one —
        // • Search your library for up to X land cards, reveal them, put them into your hand, then shuffle your library.
        this.getSpellAbility().addEffect(new NyleasInterventionEffect());

        // • Nylea's Intervention deals twice X damage to each creature with flying.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(xValue, filter)
                .setText("{this} deals twice X damage to each creature with flying")));
    }

    private NyleasIntervention(final NyleasIntervention card) {
        super(card);
    }

    @Override
    public NyleasIntervention copy() {
        return new NyleasIntervention(this);
    }
}

class NyleasInterventionEffect extends OneShotEffect {

    NyleasInterventionEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X land cards, " +
                "reveal them, put them into your hand, then shuffle";
    }

    private NyleasInterventionEffect(final NyleasInterventionEffect effect) {
        super(effect);
    }

    @Override
    public NyleasInterventionEffect copy() {
        return new NyleasInterventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                0, xValue, StaticFilters.FILTER_CARD_LAND
        ), true).apply(game, source);
    }
}
