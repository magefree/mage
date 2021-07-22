
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Briarhorn extends CardImpl {

    public Briarhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // When Briarhorn enters the battlefield, target creature gets +3/+3 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(3,3, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Evoke {1}{G}
        this.addAbility(new EvokeAbility("{1}{G}"));
    }

    private Briarhorn(final Briarhorn card) {
        super(card);
    }

    @Override
    public Briarhorn copy() {
        return new Briarhorn(this);
    }
}
