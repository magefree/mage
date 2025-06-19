package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AvenAugur extends CardImpl {

    public AvenAugur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sacrifice Aven Augur: Return up to two target creatures to their owners' hands. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ReturnToHandTargetEffect(), new SacrificeSourceCost(), IsStepCondition.getMyUpkeep()
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private AvenAugur(final AvenAugur card) {
        super(card);
    }

    @Override
    public AvenAugur copy() {
        return new AvenAugur(this);
    }
}
