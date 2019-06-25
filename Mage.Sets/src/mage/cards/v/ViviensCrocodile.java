package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensCrocodile extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(SubType.VIVIEN, "a Vivien planeswalker");
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter);

    public ViviensCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.CROCODILE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vivien's Crocodile gets +1/+1 as long as you control a Vivien planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+1 as long as you control a Vivien planeswalker"
        )));
    }

    private ViviensCrocodile(final ViviensCrocodile card) {
        super(card);
    }

    @Override
    public ViviensCrocodile copy() {
        return new ViviensCrocodile(this);
    }
}
