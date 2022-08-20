package mage.cards.u;

import mage.abilities.Mode;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UniteTheCoalition extends CardImpl {

    public UniteTheCoalition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}{B}{R}{G}");

        // Choose five. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(5);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Target permanent phases out.
        this.getSpellAbility().addEffect(new PhaseOutTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());

        // • Target player draws a card.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(1)).addTarget(new TargetPlayer()));

        // • Exile target player's graveyard.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllTargetPlayerEffect()).addTarget(new TargetPlayer()));

        // • Unite the Coalition deals 2 damage to any target.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(2)).addTarget(new TargetAnyTarget()));

        // • Destroy target artifact or enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT)));
    }

    private UniteTheCoalition(final UniteTheCoalition card) {
        super(card);
    }

    @Override
    public UniteTheCoalition copy() {
        return new UniteTheCoalition(this);
    }
}
