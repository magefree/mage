
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class GiltLeafSeer extends CardImpl {

    public GiltLeafSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, {tap}: Look at the top two cards of your library, then put them back in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(2), new ManaCostsImpl<>("{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GiltLeafSeer(final GiltLeafSeer card) {
        super(card);
    }

    @Override
    public GiltLeafSeer copy() {
        return new GiltLeafSeer(this);
    }
}