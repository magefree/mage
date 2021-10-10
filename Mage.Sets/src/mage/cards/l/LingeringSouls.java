package mage.cards.l;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LingeringSouls extends CardImpl {

    public LingeringSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");


        // Create two 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 2));
        // Flashback {1}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{1}{B}")));
    }

    private LingeringSouls(final LingeringSouls card) {
        super(card);
    }

    @Override
    public LingeringSouls copy() {
        return new LingeringSouls(this);
    }
}
