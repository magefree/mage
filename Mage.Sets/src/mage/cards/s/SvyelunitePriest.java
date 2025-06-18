package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SvyelunitePriest extends CardImpl {

    public SvyelunitePriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}{U}, {tap}: Target creature gains shroud until end of turn. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new GainAbilityTargetEffect(
                        ShroudAbility.getInstance(), Duration.EndOfTurn
                ), new ManaCostsImpl<>("{U}{U}"), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SvyelunitePriest(final SvyelunitePriest card) {
        super(card);
    }

    @Override
    public SvyelunitePriest copy() {
        return new SvyelunitePriest(this);
    }
}
