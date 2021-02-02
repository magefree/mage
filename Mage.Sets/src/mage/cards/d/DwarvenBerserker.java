
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
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
public final class DwarvenBerserker extends CardImpl {

    public DwarvenBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Dwarven Berserker becomes blocked, it gets +3/+0 and gains trample until end of turn.
        Effect effect = new BoostSourceEffect(3, 0, Duration.EndOfTurn);
        effect.setText("it gets +3/+0");
        Ability ability = new BecomesBlockedSourceTriggeredAbility(effect, false);
        effect = new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DwarvenBerserker(final DwarvenBerserker card) {
        super(card);
    }

    @Override
    public DwarvenBerserker copy() {
        return new DwarvenBerserker(this);
    }
}
