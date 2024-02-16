
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class GunganCaptain extends CardImpl {

    public GunganCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.GUNGAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Gungan Captain deals damage to a creture, tap that creature. That creature does not untap during its controllers untap step.
        Ability ability = new DealsDamageToACreatureTriggeredAbility(new TapTargetEffect("tap that creature"), false, false, true);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        this.addAbility(ability);
    }

    private GunganCaptain(final GunganCaptain card) {
        super(card);
    }

    @Override
    public GunganCaptain copy() {
        return new GunganCaptain(this);
    }
}
