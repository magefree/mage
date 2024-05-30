package mage.cards.u;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnfathomableTruths extends CardImpl {

    public UnfathomableTruths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");
        
        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Draw three cards and create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken()).concatBy("and"));
    }

    private UnfathomableTruths(final UnfathomableTruths card) {
        super(card);
    }

    @Override
    public UnfathomableTruths copy() {
        return new UnfathomableTruths(this);
    }
}
