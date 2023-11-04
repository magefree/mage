package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ForgottenMonument extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CAVE, "Caves you control");

    public ForgottenMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Other Caves you control have "{T}, Pay 1 life: Add one mana of any color."
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter, true)
        ));
    }

    private ForgottenMonument(final ForgottenMonument card) {
        super(card);
    }

    @Override
    public ForgottenMonument copy() {
        return new ForgottenMonument(this);
    }
}
