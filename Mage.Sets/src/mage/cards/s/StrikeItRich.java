package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrikeItRich extends CardImpl {

    public StrikeItRich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Create a Treasure token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));

        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{R}")));
    }

    private StrikeItRich(final StrikeItRich card) {
        super(card);
    }

    @Override
    public StrikeItRich copy() {
        return new StrikeItRich(this);
    }
}
