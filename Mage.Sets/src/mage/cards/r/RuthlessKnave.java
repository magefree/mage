
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class RuthlessKnave extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("three Treasures");

    static {
        filter.add(SubType.TREASURE.getPredicate());
    }

    public RuthlessKnave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {2}{B}, Sacrifice a creature: Create two colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken(), 2), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);

        // Sacrifice three Treasures: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, false))
        ));
    }

    private RuthlessKnave(final RuthlessKnave card) {
        super(card);
    }

    @Override
    public RuthlessKnave copy() {
        return new RuthlessKnave(this);
    }
}
