package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawSecondCardTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrencragPyromancer extends CardImpl {

    public IrencragPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card each turn, Irencrag Pyromancer deals 3 damage to any target.
        Ability ability = new DrawSecondCardTriggeredAbility(new DamageTargetEffect(3), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private IrencragPyromancer(final IrencragPyromancer card) {
        super(card);
    }

    @Override
    public IrencragPyromancer copy() {
        return new IrencragPyromancer(this);
    }
}
