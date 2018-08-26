

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public final class RustedRelic extends CardImpl {

    public RustedRelic (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                        new ConditionalContinuousEffect(
                                new BecomesCreatureSourceEffect(new RustedRelicToken(), "artifact", Duration.WhileOnBattlefield),
                                MetalcraftCondition.instance,
                                "<i>Metalcraft</i> &mdash; {this} is a 5/5 Golem artifact creature as long as you control three or more artifacts")));
    }

    public RustedRelic (final RustedRelic card) {
        super(card);
    }

    @Override
    public RustedRelic copy() {
        return new RustedRelic(this);
    }
}

class RustedRelicToken extends TokenImpl {

    public RustedRelicToken() {
        super("Rusted Relic", "5/5 Golem artifact creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOLEM);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }
    public RustedRelicToken(final RustedRelicToken token) {
        super(token);
    }

    public RustedRelicToken copy() {
        return new RustedRelicToken(this);
    }
}