
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.InsectToken;
import mage.game.permanent.token.OneDozenEyesBeastToken;

/**
 *
 * @author LevelX2
 */
public final class OneDozenEyes extends CardImpl {

    public OneDozenEyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);
        // Create a 5/5 green Beast creature token;
        this.getSpellAbility().addEffect(new CreateTokenEffect(new OneDozenEyesBeastToken()));
        // or create five 1/1 green Insect creature tokens.
        Mode mode = new Mode(new CreateTokenEffect(new InsectToken(), 5));
        this.getSpellAbility().addMode(mode);
        // Entwine {G}{G}{G}
        this.addAbility(new EntwineAbility("{G}{G}{G}"));
    }

    private OneDozenEyes(final OneDozenEyes card) {
        super(card);
    }

    @Override
    public OneDozenEyes copy() {
        return new OneDozenEyes(this);
    }
}
