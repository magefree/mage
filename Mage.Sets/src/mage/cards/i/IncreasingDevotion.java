package mage.cards.i;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingDevotion extends CardImpl {

    public IncreasingDevotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Create five 1/1 white Human creature tokens. If this spell was cast from a graveyard, create ten of those tokens instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateTokenEffect(new HumanToken(), 10), new CreateTokenEffect(new HumanToken(), 5),
                CastFromGraveyardSourceCondition.instance, "create five 1/1 white Human creature tokens. " +
                "If this spell was cast from a graveyard, create ten of those tokens instead"
        ));

        // Flashback {7}{W}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{7}{W}{W}")));
    }

    private IncreasingDevotion(final IncreasingDevotion card) {
        super(card);
    }

    @Override
    public IncreasingDevotion copy() {
        return new IncreasingDevotion(this);
    }
}
