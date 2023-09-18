package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class UlvenwaldTracker extends CardImpl {

    public UlvenwaldTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}: Target creature you control fights another target creature.
        Ability ability = new SimpleActivatedAbility(new FightTargetsEffect(false), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        Target controlledTarget = new TargetControlledCreaturePermanent();
        controlledTarget.setTargetTag(1);
        ability.addTarget(controlledTarget);
        Target secondTarget = new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2);
        secondTarget.setTargetTag(2);
        ability.addTarget(secondTarget);
        this.addAbility(ability);
    }

    private UlvenwaldTracker(final UlvenwaldTracker card) {
        super(card);
    }

    @Override
    public UlvenwaldTracker copy() {
        return new UlvenwaldTracker(this);
    }
}
