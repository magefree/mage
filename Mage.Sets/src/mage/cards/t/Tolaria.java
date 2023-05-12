
package mage.cards.t;

import java.util.UUID;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Tolaria extends CardImpl {

    public Tolaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Target creature loses banding and all "bands with other" abilities until end of turn. Activate this ability only during any upkeep step.
        ActivatedAbilityImpl ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new LoseAbilityTargetEffect(BandingAbility.getInstance(), Duration.EndOfTurn), new TapSourceCost(), new IsStepCondition(PhaseStep.UPKEEP, false),
                "{T}: Target creature loses banding and all \"bands with other\" abilities until end of turn. Activate only during any upkeep step.");
        ability.addEffect(new LoseAbilityTargetEffect(new BandsWithOtherAbility(), Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Tolaria(final Tolaria card) {
        super(card);
    }

    @Override
    public Tolaria copy() {
        return new Tolaria(this);
    }
}
