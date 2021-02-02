package mage.cards.s;

import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShadowSlice extends CardImpl {

    public ShadowSlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Target Opponent loses 3 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Cipher (Then you may exilce this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        this.getSpellAbility().addEffect(new CipherEffect());

    }

    private ShadowSlice(final ShadowSlice card) {
        super(card);
    }

    @Override
    public ShadowSlice copy() {
        return new ShadowSlice(this);
    }
}
