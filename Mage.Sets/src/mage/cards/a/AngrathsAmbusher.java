
package mage.cards.a;

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
public final class AngrathsAmbusher extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.ANGRATH.getPredicate());
    }

    public AngrathsAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Angrath's Ambusher gets +2/+0 as long as you control an Angrath planeswalker.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "{this} gets +2/+0 as long as you control an Angrath planeswalker")));
    }

    private AngrathsAmbusher(final AngrathsAmbusher card) {
        super(card);
    }

    @Override
    public AngrathsAmbusher copy() {
        return new AngrathsAmbusher(this);
    }
}
