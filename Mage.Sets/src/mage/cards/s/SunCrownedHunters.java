
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class SunCrownedHunters extends CardImpl {

    public SunCrownedHunters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // <i>Enrage</i> &mdash; Whenever Sun-Crowned Hunters is dealt damage, it deals 3 damage to target opponent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DamageTargetEffect(3).setText("it deals 3 damage to target opponent"), false, true
        );
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    public SunCrownedHunters(final SunCrownedHunters card) {
        super(card);
    }

    @Override
    public SunCrownedHunters copy() {
        return new SunCrownedHunters(this);
    }
}
