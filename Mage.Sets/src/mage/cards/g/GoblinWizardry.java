package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinWizardToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoblinWizardry extends CardImpl {

    public GoblinWizardry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Create two 1/1 red Goblin Wizard creature tokens with prowess.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinWizardToken(), 2));
    }

    private GoblinWizardry(final GoblinWizardry card) {
        super(card);
    }

    @Override
    public GoblinWizardry copy() {
        return new GoblinWizardry(this);
    }
}
