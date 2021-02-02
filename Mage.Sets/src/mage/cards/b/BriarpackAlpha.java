
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class BriarpackAlpha extends CardImpl {

    public BriarpackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlashAbility.getInstance());
        // When Briarpack Alpha enters the battlefield, target creature gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BriarpackAlpha(final BriarpackAlpha card) {
        super(card);
    }

    @Override
    public BriarpackAlpha copy() {
        return new BriarpackAlpha(this);
    }
}
