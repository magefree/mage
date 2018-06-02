
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class InfectiousHost extends CardImpl {

    public InfectiousHost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Infectious Host dies, target player loses 2 life.
        Ability ability = new DiesTriggeredAbility(new LoseLifeTargetEffect(2), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public InfectiousHost(final InfectiousHost card) {
        super(card);
    }

    @Override
    public InfectiousHost copy() {
        return new InfectiousHost(this);
    }
}
