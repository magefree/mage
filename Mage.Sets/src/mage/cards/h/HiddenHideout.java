package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class HiddenHideout extends CardImpl {

    private static final FilterControlledCreaturePermanent filter =
        new FilterControlledCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public HiddenHideout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add one mana of any color in your commander's color identity.
        this.addAbility(new CommanderColorIdentityManaAbility());

        // {2}, {T}: Target creature you control with a counter on it gains lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(LifelinkAbility.getInstance()),
            new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HiddenHideout(final HiddenHideout card) {
        super(card);
    }

    @Override
    public HiddenHideout copy() {
        return new HiddenHideout(this);
    }
}
