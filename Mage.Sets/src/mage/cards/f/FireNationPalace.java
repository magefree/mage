package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationPalace extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("you control a basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public FireNationPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {1}{R}, {T}: Target creature you control gains firebending 4 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(new FirebendingAbility(4)), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private FireNationPalace(final FireNationPalace card) {
        super(card);
    }

    @Override
    public FireNationPalace copy() {
        return new FireNationPalace(this);
    }
}
