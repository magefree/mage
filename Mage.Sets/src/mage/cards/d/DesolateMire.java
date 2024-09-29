package mage.cards.d;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesolateMire extends CardImpl {

    public DesolateMire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {1}, {T}: Add {W}{B}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 1, 0, 0, 0, 0, 0), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DesolateMire(final DesolateMire card) {
        super(card);
    }

    @Override
    public DesolateMire copy() {
        return new DesolateMire(this);
    }
}
