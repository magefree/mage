package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class GrimDiscovery extends CardImpl {

    private static final FilterLandCard filterLandCard = new FilterLandCard("land card from your graveyard");

    public GrimDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Return target creature card from your graveyard to your hand;
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD).withChooseHint("return to hand"));
        // and/or return target land card from your graveyard to your hand.
        Mode mode1 = new Mode();
        mode1.addEffect(new ReturnToHandTargetEffect());
        mode1.addTarget(new TargetCardInYourGraveyard(filterLandCard).withChooseHint("return to hand"));
        this.getSpellAbility().addMode(mode1);
    }

    private GrimDiscovery(final GrimDiscovery card) {
        super(card);
    }

    @Override
    public GrimDiscovery copy() {
        return new GrimDiscovery(this);
    }
}
