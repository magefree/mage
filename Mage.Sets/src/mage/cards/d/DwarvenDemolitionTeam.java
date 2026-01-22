package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DwarvenDemolitionTeam extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WALL, "Wall");

    public DwarvenDemolitionTeam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target Wall.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DwarvenDemolitionTeam(final DwarvenDemolitionTeam card) {
        super(card);
    }

    @Override
    public DwarvenDemolitionTeam copy() {
        return new DwarvenDemolitionTeam(this);
    }
}
