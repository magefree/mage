package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlABasicLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireNationPalace extends CardImpl {

    public FireNationPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a basic land.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(YouControlABasicLandCondition.instance)
                .addHint(YouControlABasicLandCondition.getHint()));

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
