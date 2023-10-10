
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.CompoundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author Loki, noxx
 */
public final class AkromasMemorial extends CardImpl {

    public AkromasMemorial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{7}");
        this.supertype.add(SuperType.LEGENDARY);

        // Creatures you control have flying, first strike, vigilance, trample, haste, and protection from black and from red.
        CompoundAbility compoundAbilities = new CompoundAbility(FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), VigilanceAbility.getInstance(),
                TrampleAbility.getInstance(), HasteAbility.getInstance(), ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(compoundAbilities, Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"))));
    }

    private AkromasMemorial(final AkromasMemorial card) {
        super(card);
    }

    @Override
    public AkromasMemorial copy() {
        return new AkromasMemorial(this);
    }
}
