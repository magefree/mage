package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class ArisenGorgon extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent(
                    SubType.LILIANA,
                    "a Liliana planeswalker"
            );

    public ArisenGorgon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GORGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Arisen Gorgon has deathtouch as long as you control a Liliana planeswalker.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                DeathtouchAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "{this} has deathtouch as long as you control a Liliana planeswalker"
                )
        ));
    }

    private ArisenGorgon(final ArisenGorgon card) {
        super(card);
    }

    @Override
    public ArisenGorgon copy() {
        return new ArisenGorgon(this);
    }
}
