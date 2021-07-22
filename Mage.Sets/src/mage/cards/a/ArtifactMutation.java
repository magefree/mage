package mage.cards.a;

import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class ArtifactMutation extends CardImpl {

    public ArtifactMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        // Destroy target artifact. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // create X 1/1 green Saproling creature tokens, where X is that artifact's converted mana cost.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), TargetManaValue.instance).setText("create X 1/1 green Saproling creature tokens, where X is that artifact's mana value"));
    }

    private ArtifactMutation(final ArtifactMutation card) {
        super(card);
    }

    @Override
    public ArtifactMutation copy() {
        return new ArtifactMutation(this);
    }
}
