package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.BeastToken2;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowbeastSighting extends CardImpl {

    public ShadowbeastSighting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Create a 4/4 green Beast creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken2()));

        // Flashback {6}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{G}")));
    }

    private ShadowbeastSighting(final ShadowbeastSighting card) {
        super(card);
    }

    @Override
    public ShadowbeastSighting copy() {
        return new ShadowbeastSighting(this);
    }
}
