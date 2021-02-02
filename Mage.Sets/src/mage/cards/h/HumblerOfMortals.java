
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HumblerOfMortals extends CardImpl {

    public HumblerOfMortals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Constellation - Whenever Humbler of Mortals or another enchantment enters the battlefield under your control, creatures you control gain trample until end of turn.
        this.addAbility(new ConstellationAbility(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("creatures"))));
    }

    private HumblerOfMortals(final HumblerOfMortals card) {
        super(card);
    }

    @Override
    public HumblerOfMortals copy() {
        return new HumblerOfMortals(this);
    }
}
