
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HeraldOfTheFair extends CardImpl {

    public HeraldOfTheFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Herald of the Fair enters the battlefield, target creature you control gets +1/+1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

    }

    private HeraldOfTheFair(final HeraldOfTheFair card) {
        super(card);
    }

    @Override
    public HeraldOfTheFair copy() {
        return new HeraldOfTheFair(this);
    }
}
