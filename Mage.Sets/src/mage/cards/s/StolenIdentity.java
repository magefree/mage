package mage.cards.s;

import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class StolenIdentity extends CardImpl {

    public StolenIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");

        // Create a token that's a copy of target artifact or creature.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private StolenIdentity(final StolenIdentity card) {
        super(card);
    }

    @Override
    public StolenIdentity copy() {
        return new StolenIdentity(this);
    }
}
