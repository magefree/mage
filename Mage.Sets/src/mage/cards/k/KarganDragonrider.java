package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class KarganDragonrider extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.DRAGON, "a Dragon");

    public KarganDragonrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you control a Dragon, Kargan Dragonrider has flying.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "as long as you control a Dragon, {this} has flying"
                )
        ));
    }

    private KarganDragonrider(final KarganDragonrider card) {
        super(card);
    }

    @Override
    public KarganDragonrider copy() {
        return new KarganDragonrider(this);
    }
}
