
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class ShinenOfLifesRoar extends CardImpl {

    public ShinenOfLifesRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // All creatures able to block Shinen of Life's Roar do so.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect()));
        // Channel - {2}{G}{G}, Discard Shinen of Life's Roar: All creatures able to block target creature this turn do so.
        Ability ability = new ChannelAbility("{2}{G}{G}", new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ShinenOfLifesRoar(final ShinenOfLifesRoar card) {
        super(card);
    }

    @Override
    public ShinenOfLifesRoar copy() {
        return new ShinenOfLifesRoar(this);
    }
}
