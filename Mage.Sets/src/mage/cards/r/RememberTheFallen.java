package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class RememberTheFallen extends CardImpl {

    private static final FilterArtifactCard filterArtifact = new FilterArtifactCard("artifact card from your graveyard");

    public RememberTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD).withChooseHint("return to hand"));

        // • Return target artifact card from your graveyard to your hand.
        Mode mode = new Mode();
        mode.addEffect(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(filterArtifact).withChooseHint("return to hand"));
        this.getSpellAbility().addMode(mode);

    }

    private RememberTheFallen(final RememberTheFallen card) {
        super(card);
    }

    @Override
    public RememberTheFallen copy() {
        return new RememberTheFallen(this);
    }
}
