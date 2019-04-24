
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GlimpseTheSunGod extends CardImpl {

    public GlimpseTheSunGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // Tap X target creatures. Scry 1.
        this.getSpellAbility().addEffect(new TapTargetEffect("X target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, FILTER_PERMANENT_CREATURES, false));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    public GlimpseTheSunGod(final GlimpseTheSunGod card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberToTap = ability.getManaCostsToPay().getX();
            numberToTap = Math.min(game.getBattlefield().count(FILTER_PERMANENT_CREATURES, ability.getSourceId(), ability.getControllerId(), game), numberToTap);
            ability.addTarget(new TargetCreaturePermanent(numberToTap, numberToTap, FILTER_PERMANENT_CREATURES, false));
        }
    }

    @Override
    public GlimpseTheSunGod copy() {
        return new GlimpseTheSunGod(this);
    }
}
