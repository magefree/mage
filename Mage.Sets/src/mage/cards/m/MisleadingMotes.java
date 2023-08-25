package mage.cards.m;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MisleadingMotes extends CardImpl {

    public MisleadingMotes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target creature's owner puts it on the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(
                "target creature's owner puts it on the top or bottom of their library"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MisleadingMotes(final MisleadingMotes card) {
        super(card);
    }

    @Override
    public MisleadingMotes copy() {
        return new MisleadingMotes(this);
    }
}
