package mage.cards.b;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ElephantToken;
import mage.game.permanent.token.SnakeToken;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BestialMenace extends CardImpl {

    public BestialMenace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new SnakeToken()));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WolfToken()).setText(", a 2/2 green Wolf creature token"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElephantToken()).setText(", and a 3/3 green Elephant creature token"));
    }

    private BestialMenace(final BestialMenace card) {
        super(card);
    }

    @Override
    public BestialMenace copy() {
        return new BestialMenace(this);
    }
}
