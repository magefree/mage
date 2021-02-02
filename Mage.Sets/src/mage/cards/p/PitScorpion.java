
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class PitScorpion extends CardImpl {

    public PitScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Pit Scorpion deals damage to a player, that player gets a poison counter.
        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("that player gets a poison counter");
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(effect, false, true));
    }

    private PitScorpion(final PitScorpion card) {
        super(card);
    }

    @Override
    public PitScorpion copy() {
        return new PitScorpion(this);
    }
}
