package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevengeOfTheDrowned extends CardImpl {

    public RevengeOfTheDrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target creature's owner puts it on the top or bottom of their library. You create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(
                "target creature's owner puts it on the top or bottom of their library"
        ));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()).concatBy("You"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RevengeOfTheDrowned(final RevengeOfTheDrowned card) {
        super(card);
    }

    @Override
    public RevengeOfTheDrowned copy() {
        return new RevengeOfTheDrowned(this);
    }
}
