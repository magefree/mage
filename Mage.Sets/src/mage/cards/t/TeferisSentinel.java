
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class TeferisSentinel extends CardImpl {

    public TeferisSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(6);

        // As long as you control a Teferi planeswalker, Teferi's Sentinel gets +4/+0.
        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.TEFERI.getPredicate());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(4, 0, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "As long as you control a Teferi planeswalker, {this} gets +4/+0")));
    }

    private TeferisSentinel(final TeferisSentinel card) {
        super(card);
    }

    @Override
    public TeferisSentinel copy() {
        return new TeferisSentinel(this);
    }
}
