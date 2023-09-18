package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author TheElk801
 */
public final class TezzeretsStrider extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.TEZZERET,
                    "a Tezzeret planeswalker"
            );

    public TezzeretsStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As long as you control a Tezzeret planeswalker, Tezzeret's Strider has menace.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                new MenaceAbility(true),
                                Duration.WhileOnBattlefield
                        ),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "As long as you control a Tezzeret planeswalker, {this} has menace. " +
                                "<i>(It can't be blocked except by two or more creatures.)</i>"
                )
        ));
    }

    private TezzeretsStrider(final TezzeretsStrider card) {
        super(card);
    }

    @Override
    public TezzeretsStrider copy() {
        return new TezzeretsStrider(this);
    }
}
