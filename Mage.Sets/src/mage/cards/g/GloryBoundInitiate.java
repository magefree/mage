
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class GloryBoundInitiate extends CardImpl {

    public GloryBoundInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // You may exert Glory-Bound Initiate as it attacks. When you do, it gets +1/+3 and gains lifelink until end of turn.
        Effect effect = new BoostSourceEffect(1, 3, Duration.EndOfTurn);
        effect.setText("it gets +1/+3");
        BecomesExertSourceTriggeredAbility ability = new BecomesExertSourceTriggeredAbility(effect);
        effect = new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink until end of turn");
        ability.addEffect(effect);
        this.addAbility(new ExertAbility(ability));
    }

    private GloryBoundInitiate(final GloryBoundInitiate card) {
        super(card);
    }

    @Override
    public GloryBoundInitiate copy() {
        return new GloryBoundInitiate(this);
    }
}
