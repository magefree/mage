package mage.cards.f;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DinosaurHasteToken;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForbiddenFriendship extends CardImpl {

    public ForbiddenFriendship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Create a 1/1 red Dinosaur creature token with haste and a 1/1 white Human Soldier creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new DinosaurHasteToken()));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HumanSoldierToken())
                 .setText("and a 1/1 white Human Soldier creature token"));
    }

    private ForbiddenFriendship(final ForbiddenFriendship card) {
        super(card);
    }

    @Override
    public ForbiddenFriendship copy() {
        return new ForbiddenFriendship(this);
    }
}
