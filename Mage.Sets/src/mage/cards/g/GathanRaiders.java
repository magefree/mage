
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class GathanRaiders extends CardImpl {

    public GathanRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hellbent - Gathan Raiders gets +2/+2 as long as you have no cards in hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2,2,Duration.WhileOnBattlefield), HellbentCondition.instance,
                "<i>Hellbent</i> &mdash; {this} gets +2/+2 as long as you have no cards in hand")));
        // Morph-Discard a card.
        this.addAbility(new MorphAbility(new DiscardCardCost()));
    }

    private GathanRaiders(final GathanRaiders card) {
        super(card);
    }

    @Override
    public GathanRaiders copy() {
        return new GathanRaiders(this);
    }
}
