
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class NivixBarrier extends CardImpl {

    public NivixBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Nivix Barrier enters the battlefield, target attacking creature gets -4/-0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-4, 0, Duration.EndOfTurn), false);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private NivixBarrier(final NivixBarrier card) {
        super(card);
    }

    @Override
    public NivixBarrier copy() {
        return new NivixBarrier(this);
    }
}
