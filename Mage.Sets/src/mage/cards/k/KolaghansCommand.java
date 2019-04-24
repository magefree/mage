
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class KolaghansCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public KolaghansCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Return target creature card from your graveyard to your hand;
        this.getSpellAbility().getEffects().add(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().getTargets().add(new TargetCardInYourGraveyard(1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // or Target player discards a card;
        Mode mode = new Mode();
        mode.getEffects().add(new DiscardTargetEffect(1));
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().getModes().addMode(mode);

        // or Destroy target artifact;
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetPermanent(filter));
        this.getSpellAbility().getModes().addMode(mode);

        // or Kolaghan's Command deals 2 damage to any target.
        mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(2));
        mode.getTargets().add(new TargetAnyTarget());
        this.getSpellAbility().getModes().addMode(mode);
    }

    public KolaghansCommand(final KolaghansCommand card) {
        super(card);
    }

    @Override
    public KolaghansCommand copy() {
        return new KolaghansCommand(this);
    }
}
