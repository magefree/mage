package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KlingonWarriorToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class BoardingAction extends CardImpl {

    public BoardingAction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Create a 2/1 red Klingon Warrior creature token with haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KlingonWarriorToken()));

        // Beam me up {2}{R}
        this.addAbility(new BeamMeUpAbility(this, "{2}{R}"));
    }

    private BoardingAction(final BoardingAction card) {
        super(card);
    }

    @Override
    public BoardingAction copy() {
        return new BoardingAction(this);
    }
}
