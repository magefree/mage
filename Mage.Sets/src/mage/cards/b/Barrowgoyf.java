package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessPlusOneSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Barrowgoyf extends CardImpl {

    private static final DynamicValue powerValue = CardTypesInGraveyardCount.ALL;

    public Barrowgoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Barrowgoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessPlusOneSourceEffect(powerValue)));

        // Whenever Barrowgoyf deals combat damage to a player, you may mill that many cards. If you do, you may put a creature card from among them into your hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new MillThenPutInHandEffect(SavedDamageValue.MANY, StaticFilters.FILTER_CARD_CREATURE)
                        .setText("you may mill that many cards. If you do, you may put a creature card from among them into your hand")
        ));
    }

    private Barrowgoyf(final Barrowgoyf card) {
        super(card);
    }

    @Override
    public Barrowgoyf copy() {
        return new Barrowgoyf(this);
    }
}
