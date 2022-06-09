
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public final class SkyshroudPoacher extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("Elf permanent card");
    
    static {
        filter.add(SubType.ELF.getPredicate());
    }
    
    public SkyshroudPoacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}, {tap}: Search your library for an Elf permanent card and put it onto the battlefield. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private SkyshroudPoacher(final SkyshroudPoacher card) {
        super(card);
    }

    @Override
    public SkyshroudPoacher copy() {
        return new SkyshroudPoacher(this);
    }
}
