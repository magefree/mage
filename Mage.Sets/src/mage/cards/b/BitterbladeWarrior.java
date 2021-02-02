
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BitterbladeWarrior extends CardImpl {

    public BitterbladeWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.JACKAL, SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // You may exert Bitterblade Warrior as it attacks. When you do, it gets +1/+0 and gains deathtouch until end of turn.
        Effect effect = new BoostSourceEffect(1, 0, Duration.EndOfTurn);
        effect.setText("it gets +1/+0");
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(effect);
        effect = new GainAbilitySourceEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains deathtouch until end of turn");
        ability.addEffect(effect);
        this.addAbility(new ExertAbility(ability));
    }

    private BitterbladeWarrior(final BitterbladeWarrior card) {
        super(card);
    }

    @Override
    public BitterbladeWarrior copy() {
        return new BitterbladeWarrior(this);
    }
}
