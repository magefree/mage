package mage.cards.o;

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
 * @author LevelX2
 */
public final class OrcishLumberjack extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public OrcishLumberjack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.ORC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Sacrifice a Forest: Add three mana in any combination of {R} and/or {G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(
                3, ColoredManaSymbol.R, ColoredManaSymbol.G
        ), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);

    }

    private OrcishLumberjack(final OrcishLumberjack card) {
        super(card);
    }

    @Override
    public OrcishLumberjack copy() {
        return new OrcishLumberjack(this);
    }
}
