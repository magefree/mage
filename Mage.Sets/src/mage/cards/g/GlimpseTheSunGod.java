
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;

/**
 * @author LevelX2
 */
public final class GlimpseTheSunGod extends CardImpl {

    public GlimpseTheSunGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // Tap X target creatures. Scry 1.
        this.getSpellAbility().addEffect(new TapTargetEffect("X target creatures"));
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().setTargetAdjuster(GlimpseTheSunGodAdjuster.instance);
    }

    public GlimpseTheSunGod(final GlimpseTheSunGod card) {
        super(card);
    }

    @Override
    public GlimpseTheSunGod copy() {
        return new GlimpseTheSunGod(this);
    }
}

enum GlimpseTheSunGodAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numberToTap = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(numberToTap, numberToTap, FILTER_PERMANENT_CREATURES, false));
    }
}