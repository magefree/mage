package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassProduction extends CardImpl {

    public MassProduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Create four 1/1 colorless Soldier artifact creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierArtifactToken(), 4));
    }

    private MassProduction(final MassProduction card) {
        super(card);
    }

    @Override
    public MassProduction copy() {
        return new MassProduction(this);
    }
}
