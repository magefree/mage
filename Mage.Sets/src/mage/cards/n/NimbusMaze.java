
package mage.cards.n;

import java.util.UUID;
import mage.Mana;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author dustinconrad
 */
public final class NimbusMaze extends CardImpl {

    private static final FilterControlledPermanent controlIsland = new FilterControlledPermanent("you control an Island");
    private static final FilterControlledPermanent controlPlains = new FilterControlledPermanent("you control a Plains");

    static {
        controlIsland.add(SubType.ISLAND.getPredicate());
        controlPlains.add(SubType.PLAINS.getPredicate());
    }

    public NimbusMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {W}. Activate this ability only if you control an Island.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.WhiteMana(1)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(controlIsland)));
        // {tap}: Add {U}. Activate this ability only if you control a Plains.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlueMana(1)),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(controlPlains)));
    }

    private NimbusMaze(final NimbusMaze card) {
        super(card);
    }

    @Override
    public NimbusMaze copy() {
        return new NimbusMaze(this);
    }
}

