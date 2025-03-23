package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeritageReclamation extends CardImpl {

    public HeritageReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect())
                .addTarget(new TargetEnchantmentPermanent()));

        // * Exile up to one target card from a graveyard. Draw a card.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect())
                .addEffect(new DrawCardSourceControllerEffect(1))
                .addTarget(new TargetCardInGraveyard(0, 1)));
    }

    private HeritageReclamation(final HeritageReclamation card) {
        super(card);
    }

    @Override
    public HeritageReclamation copy() {
        return new HeritageReclamation(this);
    }
}
