package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HarmonizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.Elephant55Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MammothBellow extends CardImpl {

    public MammothBellow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{U}{R}");

        // Create a 5/5 green Elephant creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new Elephant55Token()));

        // Harmonize {5}{G}{U}{R}
        this.addAbility(new HarmonizeAbility(this, "{5}{G}{U}{R}"));
    }

    private MammothBellow(final MammothBellow card) {
        super(card);
    }

    @Override
    public MammothBellow copy() {
        return new MammothBellow(this);
    }
}
