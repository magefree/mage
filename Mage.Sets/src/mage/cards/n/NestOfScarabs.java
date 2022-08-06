package mage.cards.n;

import mage.abilities.common.PutCounterOnCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.NestOfScarabsBlackInsectToken;

import java.util.UUID;

/**
 * @author stravant
 */
public final class NestOfScarabs extends CardImpl {

    public NestOfScarabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect creature tokens.
        this.addAbility(new PutCounterOnCreatureTriggeredAbility(new CreateTokenEffect(new NestOfScarabsBlackInsectToken(), new EffectKeyValue("countersAdded"))
                .setText("create that many 1/1 black Insect creature tokens"), CounterType.M1M1.createInstance()));
    }

    private NestOfScarabs(final NestOfScarabs card) {
        super(card);
    }

    @Override
    public NestOfScarabs copy() {
        return new NestOfScarabs(this);
    }
}
