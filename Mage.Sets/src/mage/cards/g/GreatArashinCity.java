package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.NoFlyingSpiritWhiteToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatArashinCity extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Forest or a Plains");

    static {
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public GreatArashinCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a Forest or a Plains.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {1}{B}, {T}, Exile a creature card from your graveyard: Create a 1/1 white Spirit creature token.
        Ability ability = new SimpleActivatedAbility(new CreateTokenEffect(new NoFlyingSpiritWhiteToken()), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE)).withSourceExileZone(false));
        this.addAbility(ability);
    }

    private GreatArashinCity(final GreatArashinCity card) {
        super(card);
    }

    @Override
    public GreatArashinCity copy() {
        return new GreatArashinCity(this);
    }
}
