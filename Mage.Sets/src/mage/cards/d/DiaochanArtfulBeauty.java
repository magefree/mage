package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsChoicePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DiaochanArtfulBeauty extends CardImpl {

    public DiaochanArtfulBeauty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target creature of your choice, then destroy target creature of an opponent's choice. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DestroyTargetEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("destroy target creature of your choice, then destroy target creature of an opponent's choice"),
                new TapSourceCost(), MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetOpponentsChoicePermanent(
                1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false
        ));
        this.addAbility(ability);
    }

    private DiaochanArtfulBeauty(final DiaochanArtfulBeauty card) {
        super(card);
    }

    @Override
    public DiaochanArtfulBeauty copy() {
        return new DiaochanArtfulBeauty(this);
    }
}
