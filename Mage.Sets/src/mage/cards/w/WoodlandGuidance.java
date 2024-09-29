package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public final class WoodlandGuidance extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.FOREST, "Forests");

    public WoodlandGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Return target card from your graveyard to your hand
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());

        // Clash with an opponent. If you win, untap all Forest you control
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(new UntapAllLandsControllerEffect(filter)));

        // Remove WoodlandGuidance from the game 
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private WoodlandGuidance(final WoodlandGuidance card) {
        super(card);
    }

    @Override
    public WoodlandGuidance copy() {
        return new WoodlandGuidance(this);
    }
}
