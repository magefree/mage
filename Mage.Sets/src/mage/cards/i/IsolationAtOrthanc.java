package mage.cards.i;

import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IsolationAtOrthanc extends CardImpl {

    public IsolationAtOrthanc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Put target creature into its owner's library second from the top.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private IsolationAtOrthanc(final IsolationAtOrthanc card) {
        super(card);
    }

    @Override
    public IsolationAtOrthanc copy() {
        return new IsolationAtOrthanc(this);
    }
}
