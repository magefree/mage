package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author BursegSardaukar
 */
public final class GoblinClearcutter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public GoblinClearcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(
                3, ColoredManaSymbol.R, ColoredManaSymbol.G
        ), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private GoblinClearcutter(final GoblinClearcutter card) {
        super(card);
    }

    @Override
    public GoblinClearcutter copy() {
        return new GoblinClearcutter(this);
    }
}
