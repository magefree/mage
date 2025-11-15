package mage.cards.t;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemporalCleansing extends CardImpl {

    public TemporalCleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // The owner of target nonland permanent puts it into their library second from the top or on the bottom.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(2, true));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private TemporalCleansing(final TemporalCleansing card) {
        super(card);
    }

    @Override
    public TemporalCleansing copy() {
        return new TemporalCleansing(this);
    }
}
