package mage.cards.j;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoinTheDance extends CardImpl {

    public JoinTheDance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{W}");

        // Create two 1/1 white Human creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken(), 2));

        // Flashback {3}{G}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{G}{W}")));
    }

    private JoinTheDance(final JoinTheDance card) {
        super(card);
    }

    @Override
    public JoinTheDance copy() {
        return new JoinTheDance(this);
    }
}
