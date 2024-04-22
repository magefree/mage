

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Loki
 */
public final class SteelshaperApprentice extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment card");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public SteelshaperApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {W}, {tap}, Return Steelshaper Apprentice to its owner's hand: Search your library for an Equipment card, reveal that card, and put it into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, filter), true, true),
                new ColoredManaCost(ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);
    }

    private SteelshaperApprentice(final SteelshaperApprentice card) {
        super(card);
    }

    @Override
    public SteelshaperApprentice copy() {
        return new SteelshaperApprentice(this);
    }

}
