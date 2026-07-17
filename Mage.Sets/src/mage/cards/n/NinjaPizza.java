package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.triggers.BeginningOfSecondMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.FoodToken;

/**
 *
 * @author muz
 */
public final class NinjaPizza extends CardImpl {

    public NinjaPizza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Foods you control have "{T}, Sacrifice this artifact: Add one mana of any color."
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityControlledEffect(
                ability,
                Duration.WhileOnBattlefield,
                new FilterPermanent(SubType.FOOD, "Foods")
            )
        ));

        // At the beginning of your second main phase, create a Food token.
        this.addAbility(new BeginningOfSecondMainTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));
    }

    private NinjaPizza(final NinjaPizza card) {
        super(card);
    }

    @Override
    public NinjaPizza copy() {
        return new NinjaPizza(this);
    }
}
