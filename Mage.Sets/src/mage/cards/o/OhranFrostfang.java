package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OhranFrostfang extends CardImpl {

    public OhranFrostfang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // Attacking creatures you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        )));

        // Whenever a creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));
    }

    private OhranFrostfang(final OhranFrostfang card) {
        super(card);
    }

    @Override
    public OhranFrostfang copy() {
        return new OhranFrostfang(this);
    }
}
