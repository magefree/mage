package mage.cards.r;

import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReconstructHistory extends CardImpl {

    private static final FilterCard filterArtifact = new FilterArtifactCard();
    private static final FilterCard filterEnchantment = new FilterEnchantmentCard();
    private static final FilterCard filterInstant = new FilterCard("instant card");
    private static final FilterCard filterSorcery = new FilterCard("sorcery card");
    private static final FilterCard filterPlaneswalker = new FilterPlaneswalkerCard();

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
        filterSorcery.add(CardType.SORCERY.getPredicate());
    }

    public ReconstructHistory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{W}");

        // Return up to one target artifact card, up to one target enchantment card, up to one target instant card, up to one target sorcery card, and up to one target planeswalker card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect().setTargetPointer(new EachTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filterArtifact
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filterEnchantment
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filterInstant
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filterSorcery
        ));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, filterPlaneswalker
        ));

        // Exile Reconstruct History.
        this.getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private ReconstructHistory(final ReconstructHistory card) {
        super(card);
    }

    @Override
    public ReconstructHistory copy() {
        return new ReconstructHistory(this);
    }
}
