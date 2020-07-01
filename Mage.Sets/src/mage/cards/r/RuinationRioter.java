package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuinationRioter extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_LAND);

    public RuinationRioter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ruination Rioter dies, you may have it deal damage to any target equal to the number of land cards in your graveyard.
        Ability ability = new DiesSourceTriggeredAbility(
                new DamageTargetEffect(xValue).setText("you may have it deal damage to any target " +
                        "equal to the number of land cards in your graveyard."), true
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RuinationRioter(final RuinationRioter card) {
        super(card);
    }

    @Override
    public RuinationRioter copy() {
        return new RuinationRioter(this);
    }
}
