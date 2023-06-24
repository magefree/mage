
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class SanctumOfTheSun extends CardImpl {

    public SanctumOfTheSun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        this.nightCard = true;

        // <i>(Transforms from Azor's Gateway.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new InfoEffect("<i>(Transforms from Azor's Gateway.)</i>")).setRuleAtTheTop(true));

        // {T}: Add X mana of any one color, where X is your life total.
        this.addAbility(new DynamicManaAbility(new Mana(0, 0, 0, 0, 0, 0, 1, 0), ControllerLifeCount.instance, new TapSourceCost(),
                "Add X mana of any one color, where X is is your life total", true));

    }

    private SanctumOfTheSun(final SanctumOfTheSun card) {
        super(card);
    }

    @Override
    public SanctumOfTheSun copy() {
        return new SanctumOfTheSun(this);
    }
}
