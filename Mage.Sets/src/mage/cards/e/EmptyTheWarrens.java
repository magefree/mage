package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author Plopman
 */
public final class EmptyTheWarrens extends CardImpl {

    public EmptyTheWarrens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 2));

        // Storm
        this.addAbility(new StormAbility());
    }

    private EmptyTheWarrens(final EmptyTheWarrens card) {
        super(card);
    }

    @Override
    public EmptyTheWarrens copy() {
        return new EmptyTheWarrens(this);
    }
}
