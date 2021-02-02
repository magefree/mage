
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SpringjackKnight extends CardImpl {

    public SpringjackKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Whenever {this} attacks, clash with an opponent. If you win, target creature gains double strike until end of turn.
        Ability ability = new AttacksTriggeredAbility(new DoIfClashWonEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn)), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SpringjackKnight(final SpringjackKnight card) {
        super(card);
    }

    @Override
    public SpringjackKnight copy() {
        return new SpringjackKnight(this);
    }
}
