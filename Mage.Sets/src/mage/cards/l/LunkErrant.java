
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class LunkErrant extends CardImpl {

    public LunkErrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Lunk Errant attacks alone, it gets +1/+1 and gains trample until end of turn.
        Effect effect = new BoostSourceEffect(1, 1, Duration.EndOfTurn);
        effect.setText("it gets +1/+1");
        Ability ability = new AttacksAloneSourceTriggeredAbility(effect);
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private LunkErrant(final LunkErrant card) {
        super(card);
    }

    @Override
    public LunkErrant copy() {
        return new LunkErrant(this);
    }
}
