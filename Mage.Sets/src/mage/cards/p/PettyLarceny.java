package mage.cards.p;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class PettyLarceny extends CardImpl {

    public PettyLarceny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");
        

        // Freerunning {1}{B}
        this.addAbility(new FreerunningAbility("{1}{B}"));

        // Look at the top two cards of target opponent's library and exile those cards face down. You may play those cards for as long as they remain exiled, and mana of any type can be spent to cast them. Create a Treasure token.
        this.getSpellAbility().addEffect(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(
                        false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE, 2
                ).setText("Look at the top two cards of target opponent's library and exile those cards face down. You may play those cards for as long as they remain exiled, and mana of any type can be spent to cast them.")
        );
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PettyLarceny(final PettyLarceny card) {
        super(card);
    }

    @Override
    public PettyLarceny copy() {
        return new PettyLarceny(this);
    }
}
