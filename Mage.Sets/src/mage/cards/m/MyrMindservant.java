
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class MyrMindservant extends CardImpl {

    public MyrMindservant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}, {tap}: Shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleLibrarySourceEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MyrMindservant(final MyrMindservant card) {
        super(card);
    }

    @Override
    public MyrMindservant copy() {
        return new MyrMindservant(this);
    }
}
