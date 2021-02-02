
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class SuqAtaAssassin extends CardImpl {

    public SuqAtaAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Whenever Suq'Ata Assassin attacks and isn't blocked, defending player gets a poison counter.
        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("defending player gets a poison counter");
        this.addAbility(new AttacksAndIsNotBlockedTriggeredAbility(effect, false, true));
    }

    private SuqAtaAssassin(final SuqAtaAssassin card) {
        super(card);
    }

    @Override
    public SuqAtaAssassin copy() {
        return new SuqAtaAssassin(this);
    }
}
