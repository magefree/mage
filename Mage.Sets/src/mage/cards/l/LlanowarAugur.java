package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class LlanowarAugur extends CardImpl {

    public LlanowarAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Sacrifice Llanowar Augur: Target creature gets +3/+3 and gains trample until end of turn.
        // Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new BoostTargetEffect(3, 3).setText("Target creature gets +3/+3"),
                new SacrificeSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                "and gains trample until end of turn"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LlanowarAugur(final LlanowarAugur card) {
        super(card);
    }

    @Override
    public LlanowarAugur copy() {
        return new LlanowarAugur(this);
    }
}
