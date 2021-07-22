package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RishadanDockhand extends CardImpl {

    public RishadanDockhand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // {1}, {T}: Tap target land.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private RishadanDockhand(final RishadanDockhand card) {
        super(card);
    }

    @Override
    public RishadanDockhand copy() {
        return new RishadanDockhand(this);
    }
}
