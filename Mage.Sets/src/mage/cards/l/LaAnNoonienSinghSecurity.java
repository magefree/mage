package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class LaAnNoonienSinghSecurity extends CardImpl {

    public LaAnNoonienSinghSecurity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever you attack while creatures you control have total power 8 or greater, target creature can't block this turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
            new CantBlockTargetEffect(Duration.EndOfTurn), 1
        ).withTriggerCondition(FormidableCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LaAnNoonienSinghSecurity(final LaAnNoonienSinghSecurity card) {
        super(card);
    }

    @Override
    public LaAnNoonienSinghSecurity copy() {
        return new LaAnNoonienSinghSecurity(this);
    }
}
