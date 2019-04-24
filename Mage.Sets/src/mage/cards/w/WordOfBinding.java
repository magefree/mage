
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WordOfBinding extends CardImpl {

    public WordOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("X target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, FILTER_PERMANENT_CREATURES, false));
    }

    public WordOfBinding(final WordOfBinding card) {
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
    public WordOfBinding copy() {
        return new WordOfBinding(this);
    }
}
