package mage.cards.g;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BloodToken;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrislyRitual extends CardImpl {

    public GrislyRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");

        // Destroy target creature or planeswalker. Create two Blood tokens.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BloodToken(), 2));
    }

    private GrislyRitual(final GrislyRitual card) {
        super(card);
    }

    @Override
    public GrislyRitual copy() {
        return new GrislyRitual(this);
    }
}
