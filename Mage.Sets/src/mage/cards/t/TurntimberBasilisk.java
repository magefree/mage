
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TurntimberBasilisk extends CardImpl {

    public TurntimberBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BASILISK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(DeathtouchAbility.getInstance());
        // Landfall - Whenever a land enters the battlefield under your control, you may have target creature block Turntimber Basilisk this turn if able.
        LandfallAbility ability = new LandfallAbility(new MustBeBlockedByTargetSourceEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TurntimberBasilisk(final TurntimberBasilisk card) {
        super(card);
    }

    @Override
    public TurntimberBasilisk copy() {
        return new TurntimberBasilisk(this);
    }
}
