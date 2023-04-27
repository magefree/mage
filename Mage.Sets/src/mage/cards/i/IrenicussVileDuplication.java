package mage.cards.i;

import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrenicussVileDuplication extends CardImpl {

    public IrenicussVileDuplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Create a token that's a copy of target creature you control, except the token has flying and isn't legendary if that creature is legendary.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect()
                .addAdditionalAbilities(FlyingAbility.getInstance())
                .setIsntLegendary(true)
                .setText("create a token that's a copy of target creature you control, " +
                        "except the token has flying and isn't legendary if that creature is legendary"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private IrenicussVileDuplication(final IrenicussVileDuplication card) {
        super(card);
    }

    @Override
    public IrenicussVileDuplication copy() {
        return new IrenicussVileDuplication(this);
    }
}
