package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class GoldspanDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.TREASURE, "Treasures");

    public GoldspanDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Goldspan Dragon attacks or becomes the target of a spell, create a Treasure token.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()), false,
                "Whenever {this} attacks or becomes the target of a spell, ",
                new AttacksTriggeredAbility(null),
                new BecomesTargetSourceTriggeredAbility(null, StaticFilters.FILTER_SPELL_A)));

        // Treasures you control have "{T}, Sacrifice this artifact: Add two mana of any one color."
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        Cost cost = new SacrificeSourceCost();
        cost.setText("sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter)));
    }

    private GoldspanDragon(final GoldspanDragon card) {
        super(card);
    }

    @Override
    public GoldspanDragon copy() {
        return new GoldspanDragon(this);
    }
}
