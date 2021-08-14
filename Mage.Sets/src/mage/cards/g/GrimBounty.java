package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimBounty extends CardImpl {

    public GrimBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy target creature or planeswalker. Create a Treasure token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private GrimBounty(final GrimBounty card) {
        super(card);
    }

    @Override
    public GrimBounty copy() {
        return new GrimBounty(this);
    }
}
