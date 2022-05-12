package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class SurrakarMarauder extends CardImpl {

    public SurrakarMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SURRAKAR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Landfall - Whenever a land enters the battlefield under your control, Surrakar Marauder gains intimidate until end of turn. 
        // (It can't be blocked except by artifact creatures and/or creatures that share a color with it.)
        Effect effect = new GainAbilitySourceEffect(IntimidateAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains intimidate until end of turn. <i>(It can't be blocked except by artifact creatures and/or creatures that share a color with it.)</i>");
        this.addAbility(new LandfallAbility(effect));
    }

    private SurrakarMarauder(final SurrakarMarauder card) {
        super(card);
    }

    @Override
    public SurrakarMarauder copy() {
        return new SurrakarMarauder(this);
    }
}
