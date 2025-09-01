package mage.cards.d;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DustToDust extends CardImpl {

    public DustToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Exile two target artifacts.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(2));
    }

    private DustToDust(final DustToDust card) {
        super(card);
    }

    @Override
    public DustToDust copy() {
        return new DustToDust(this);
    }
}
