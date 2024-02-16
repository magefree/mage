package mage.cards.d;

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
public final class DeadlyDerision extends CardImpl {

    public DeadlyDerision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Destroy target creature or planeswalker. Create a Treasure token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private DeadlyDerision(final DeadlyDerision card) {
        super(card);
    }

    @Override
    public DeadlyDerision copy() {
        return new DeadlyDerision(this);
    }
}
