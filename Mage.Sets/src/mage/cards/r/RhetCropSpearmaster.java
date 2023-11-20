
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author stravant
 */
public final class RhetCropSpearmaster extends CardImpl {

    public RhetCropSpearmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may exert Rhet-Crop Spearmaster as it attacks. When you do, it gets +1/+0 and gains first strike until end of turn.
        Effect effect = new BoostSourceEffect(1, 0, Duration.EndOfTurn);
        effect.setText("it gets +1/+0");
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(effect);
        effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike until end of turn");
        ability.addEffect(effect);
        this.addAbility(new ExertAbility(ability));
    }

    private RhetCropSpearmaster(final RhetCropSpearmaster card) {
        super(card);
    }

    @Override
    public RhetCropSpearmaster copy() {
        return new RhetCropSpearmaster(this);
    }
}
