package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileFromZoneTargetEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerfectIntimidation extends CardImpl {

    public PerfectIntimidation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Target opponent exiles two cards from their hand.
        this.getSpellAbility().addEffect(new ExileFromZoneTargetEffect(
                Zone.HAND, StaticFilters.FILTER_CARD_CARDS, 2, false
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Remove all counters from target creature.
        this.getSpellAbility().addMode(new Mode(new RemoveAllCountersPermanentTargetEffect())
                .addTarget(new TargetCreaturePermanent()));
    }

    private PerfectIntimidation(final PerfectIntimidation card) {
        super(card);
    }

    @Override
    public PerfectIntimidation copy() {
        return new PerfectIntimidation(this);
    }
}
