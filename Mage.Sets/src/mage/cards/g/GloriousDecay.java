package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GloriousDecay extends CardImpl {

    public GloriousDecay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Glorious Decay deals 4 damage to target creature with flying.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(4))
                .addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING)));

        // * Exile target card from a graveyard. Draw a card.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect())
                .addEffect(new DrawCardSourceControllerEffect(1))
                .addTarget(new TargetCardInGraveyard()));
    }

    private GloriousDecay(final GloriousDecay card) {
        super(card);
    }

    @Override
    public GloriousDecay copy() {
        return new GloriousDecay(this);
    }
}
