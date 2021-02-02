
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.PlayLandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ConditionalManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.watchers.common.PlayLandWatcher;

/**
 *
 * @author LevelX2
 */
public final class RiverOfTears extends CardImpl {

    public RiverOfTears(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {U}. If you played a land this turn, add {B} instead.
        this.addAbility(new ConditionalManaAbility(Zone.BATTLEFIELD, new ConditionalManaEffect(
                new BasicManaEffect(Mana.BlackMana(1)),
                new BasicManaEffect(Mana.BlueMana(1)),
                PlayLandCondition.instance,
                "Add {U}. If you played a land this turn, add {B} instead"),
                new TapSourceCost()),
                new PlayLandWatcher());
    }

    private RiverOfTears(final RiverOfTears card) {
        super(card);
    }

    @Override
    public RiverOfTears copy() {
        return new RiverOfTears(this);
    }
}
