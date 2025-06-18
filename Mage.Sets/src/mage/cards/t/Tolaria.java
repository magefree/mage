package mage.cards.t;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Tolaria extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.UPKEEP, false);

    public Tolaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Target creature loses banding and all "bands with other" abilities until end of turn. Activate this ability only during any upkeep step.
        ActivatedAbilityImpl ability = new ActivateIfConditionActivatedAbility(
                new LoseAbilityTargetEffect(
                        BandingAbility.getInstance(), Duration.EndOfTurn
                ).setText("target creature loses banding"),
                new TapSourceCost(), condition
        );
        ability.addEffect(new LoseAbilityTargetEffect(
                new BandsWithOtherAbility(), Duration.EndOfTurn
        ).setText("and all \"bands with other\" abilities until end of turn"));
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
