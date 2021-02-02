
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class RootSpider extends CardImpl {

    public RootSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Root Spider blocks, it gets +1/+0 and gains first strike until end of turn.
        Effect effect = new BoostSourceEffect(1, 0, Duration.EndOfTurn);
        effect.setText("it gets +1/+0");
        Effect effect2 = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and gains first strike until end of turn");
        Ability ability = new BlocksSourceTriggeredAbility(effect, false);
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    private RootSpider(final RootSpider card) {
        super(card);
    }

    @Override
    public RootSpider copy() {
        return new RootSpider(this);
    }
}
