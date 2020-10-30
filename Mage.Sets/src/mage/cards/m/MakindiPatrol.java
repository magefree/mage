
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class MakindiPatrol extends CardImpl {

    public MakindiPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <i>Rally</i> &mdash; Whenever Makindi Patrol or another Ally enters the battlefield under your control, creatures you control gain vigilance until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures")), false));
    }

    public MakindiPatrol(final MakindiPatrol card) {
        super(card);
    }

    @Override
    public MakindiPatrol copy() {
        return new MakindiPatrol(this);
    }
}
