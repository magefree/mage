package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ChocoboToken;

/**
 *
 * @author TheElk801
 */
public final class GysahlGreens extends CardImpl {

    public GysahlGreens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Create a 2/2 green Bird creature token with "Whenever a land you control enters, this token gets +1/+0 until end of turn."
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ChocoboToken()));

        // Flashback {6}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{G}")));
    }

    private GysahlGreens(final GysahlGreens card) {
        super(card);
    }

    @Override
    public GysahlGreens copy() {
        return new GysahlGreens(this);
    }
}
