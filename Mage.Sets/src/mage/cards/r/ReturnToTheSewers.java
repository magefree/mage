package mage.cards.r;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MutagenToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ReturnToTheSewers extends CardImpl {

    public ReturnToTheSewers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target creature's owner puts it on their choice of the top or bottom of their library. You create a Mutagen token.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MutagenToken()).concatBy("You"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ReturnToTheSewers(final ReturnToTheSewers card) {
        super(card);
    }

    @Override
    public ReturnToTheSewers copy() {
        return new ReturnToTheSewers(this);
    }
}
