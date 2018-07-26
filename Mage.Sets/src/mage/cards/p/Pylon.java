package mage.cards.p;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class Pylon extends CardImpl {

    public Pylon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Pylon enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {1}, {T}: Add {W}{U} to your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 1, 0, 0, 0, 0), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public Pylon(final Pylon card) {
        super(card);
    }

    @Override
    public Pylon copy() {
        return new Pylon(this);
    }
}
