
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class SpiresideInfiltrator extends CardImpl {

    public SpiresideInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Spireside Infiltrator becomes tapped, it deals one damage to each opponent.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT, "it")));
    }

    private SpiresideInfiltrator(final SpiresideInfiltrator card) {
        super(card);
    }

    @Override
    public SpiresideInfiltrator copy() {
        return new SpiresideInfiltrator(this);
    }
}
