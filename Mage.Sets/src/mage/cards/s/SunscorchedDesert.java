
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class SunscorchedDesert extends CardImpl {

    public SunscorchedDesert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // When Sunscorced Desert enters the battlefield, it deals 1 damage to target player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private SunscorchedDesert(final SunscorchedDesert card) {
        super(card);
    }

    @Override
    public SunscorchedDesert copy() {
        return new SunscorchedDesert(this);
    }
}
