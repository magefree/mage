package mage.cards.s;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnarlSong extends CardImpl {

    public SnarlSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Converge -- Create two 0/0 green and blue Fractal creature tokens. Put X +1/+1 counters on each of them and you gain X life, where X is the number of colors of mana spent to cast this spell.
        this.getSpellAbility().addEffect(FractalToken.getEffect(
                2, ColorsOfManaSpentToCastCount.getInstance(),
                ". Put X +1/+1 counters on each of them"
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(ColorsOfManaSpentToCastCount.getInstance()).concatBy("and"));
        this.getSpellAbility().setAbilityWord(AbilityWord.CONVERGE).addHint(ColorsOfManaSpentToCastCount.getHint());
    }

    private SnarlSong(final SnarlSong card) {
        super(card);
    }

    @Override
    public SnarlSong copy() {
        return new SnarlSong(this);
    }
}
