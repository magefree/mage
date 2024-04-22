
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 * @author noxx
 */
public final class RotcrownGhoul extends CardImpl {

    public RotcrownGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rotcrown Ghoul dies, target player puts the top five cards of their library into their graveyard.
        Ability ability = new DiesSourceTriggeredAbility(new MillCardsTargetEffect(5));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RotcrownGhoul(final RotcrownGhoul card) {
        super(card);
    }

    @Override
    public RotcrownGhoul copy() {
        return new RotcrownGhoul(this);
    }
}
