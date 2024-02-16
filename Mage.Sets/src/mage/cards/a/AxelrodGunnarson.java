
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAndDiedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class AxelrodGunnarson extends CardImpl {

    public AxelrodGunnarson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature dealt damage by Axelrod Gunnarson this turn dies, you gain 1 life and Axelrod deals 1 damage to target player.
        Ability ability = new DealtDamageAndDiedTriggeredAbility(new GainLifeEffect(1), false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.NONE);
        Effect effect = new DamageTargetEffect(1);
        effect.setText("and {this} deals 1 damage to target player");
        ability.addEffect(effect);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private AxelrodGunnarson(final AxelrodGunnarson card) {
        super(card);
    }

    @Override
    public AxelrodGunnarson copy() {
        return new AxelrodGunnarson(this);
    }
}
