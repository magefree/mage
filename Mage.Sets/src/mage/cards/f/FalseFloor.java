package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalseFloor extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("untapped creatures");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public FalseFloor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // False Floor enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Creatures enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(
                new PermanentsEnterBattlefieldTappedEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
        ));

        // {2}, {T}, Exile False Floor: Exile all untapped creatures. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ExileAllEffect(filter), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private FalseFloor(final FalseFloor card) {
        super(card);
    }

    @Override
    public FalseFloor copy() {
        return new FalseFloor(this);
    }
}
