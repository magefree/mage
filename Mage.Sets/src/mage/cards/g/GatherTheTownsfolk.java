package mage.cards.g;

import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class GatherTheTownsfolk extends CardImpl {

    public GatherTheTownsfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Create two 1/1 white Human creature tokens.
        // Fateful hour - If you have 5 or less life, create five of those tokens instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new CreateTokenEffect(new HumanToken(), 5), new CreateTokenEffect(new HumanToken(), 2),
                FatefulHourCondition.instance, "Create two 1/1 white Human creature tokens. <br><i>Fateful hour</i> &mdash; If you have 5 or less life, create five of those tokens instead"));
    }

    private GatherTheTownsfolk(final GatherTheTownsfolk card) {
        super(card);
    }

    @Override
    public GatherTheTownsfolk copy() {
        return new GatherTheTownsfolk(this);
    }
}
