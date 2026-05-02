package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.common.HarnessSourceEffect;
import mage.abilities.effects.common.continuous.GainHarnessedAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author muz
 */
public final class TheMindStone extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("other target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public TheMindStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INFINITY);
        this.subtype.add(SubType.STONE);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {5}{W}, {T}: Harness The Mind Stone.
        Ability ability = new SimpleActivatedAbility(new HarnessSourceEffect(), new ManaCostsImpl<>("{5}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // ∞ -- At the beginning of your end step, exile up to one other target nonland permanent you control, then return that card to the battlefield under its owner's control.
        Ability soulStoneAbility = new BeginningOfEndStepTriggeredAbility(new ExileThenReturnTargetEffect(false, true));
        soulStoneAbility.addTarget(new TargetControlledPermanent(0, 1, filter, false));
        this.addAbility(new SimpleStaticAbility(new GainHarnessedAbilitySourceEffect(soulStoneAbility)));
    }

    private TheMindStone(final TheMindStone card) {
        super(card);
    }

    @Override
    public TheMindStone copy() {
        return new TheMindStone(this);
    }
}
