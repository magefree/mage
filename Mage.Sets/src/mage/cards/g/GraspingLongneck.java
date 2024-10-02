package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraspingLongneck extends CardImpl {

    public GraspingLongneck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Grasping Longneck dies, you gain 2 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(2)));
    }

    private GraspingLongneck(final GraspingLongneck card) {
        super(card);
    }

    @Override
    public GraspingLongneck copy() {
        return new GraspingLongneck(this);
    }
}
