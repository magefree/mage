

package mage.cards.a;

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
public final class AffaGuardHound extends CardImpl {

    public AffaGuardHound (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(0, 3, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AffaGuardHound(final AffaGuardHound card) {
        super(card);
    }

    @Override
    public AffaGuardHound copy() {
        return new AffaGuardHound(this);
    }

}
