
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class PerimeterCaptain extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public PerimeterCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Whenever a creature you control with defender blocks, you may gain 2 life.
        this.addAbility(new BlocksAllTriggeredAbility(new GainLifeEffect(2), true, filter));
    }

    private PerimeterCaptain(final PerimeterCaptain card) {
        super(card);
    }

    @Override
    public PerimeterCaptain copy() {
        return new PerimeterCaptain(this);
    }
}
