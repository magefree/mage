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
public final class AngrathsRampage extends CardImpl {

    public AngrathsRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // Choose one:
        // • Target player sacrifices an artifact.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_ARTIFACT_AN,
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

    private AngrathsRampage(final AngrathsRampage card) {
        super(card);
    }

    @Override
    public AngrathsRampage copy() {
        return new AngrathsRampage(this);
    }
}
