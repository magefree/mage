
package mage.cards.t;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class TempleOfTheFalseGod extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("you control five or more lands");

    public TempleOfTheFalseGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}{C}. Activate this ability only if you control five or more lands.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.ColorlessMana(2)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 4)));
    }

    public TempleOfTheFalseGod(final TempleOfTheFalseGod card) {
        super(card);
    }

    @Override
    public TempleOfTheFalseGod copy() {
        return new TempleOfTheFalseGod(this);
    }
}
