package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngrathsWrath extends CardImpl {

    public AngrathsWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // Choose one:
        // • Target player sacrifices an artifact.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACT,
                1, "Target player"
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // • Target player sacrifices a creature.
        Mode mode = new Mode(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                1, "Target player"
        ));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);

        // • Target player sacrifices a planeswalker.
        mode = new Mode(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_PLANESWALKER,
                1, "Target player"
        ));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private AngrathsWrath(final AngrathsWrath card) {
        super(card);
    }

    @Override
    public AngrathsWrath copy() {
        return new AngrathsWrath(this);
    }
}
