
package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki, noxx
 */
public final class AkromasMemorial extends CardImpl {

    public AkromasMemorial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{7}");
        this.supertype.add(SuperType.LEGENDARY);

        // Creatures you control have flying, first strike, vigilance, trample, haste, and protection from black and from red.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Creatures"),
                FlyingAbility.getInstance(), FirstStrikeAbility.getInstance(), VigilanceAbility.getInstance(),
                TrampleAbility.getInstance(), HasteAbility.getInstance(), ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED))));
    }

    private AkromasMemorial(final AkromasMemorial card) {
        super(card);
    }

    @Override
    public AkromasMemorial copy() {
        return new AkromasMemorial(this);
    }
}
