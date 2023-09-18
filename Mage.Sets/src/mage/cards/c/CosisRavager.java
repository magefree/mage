
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Loki
 */
public final class CosisRavager extends CardImpl {

    public CosisRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new LandfallAbility(new DamageTargetEffect(1), true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private CosisRavager(final CosisRavager card) {
        super(card);
    }

    @Override
    public CosisRavager copy() {
        return new CosisRavager(this);
    }

}
