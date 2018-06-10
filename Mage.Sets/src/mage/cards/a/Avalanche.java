
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class Avalanche extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow lands");

    public Avalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{R}{R}");

        // Destroy X target snow lands.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target snow lands"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetPermanent(xValue, xValue, filter, false));
        }
    }

    public Avalanche(final Avalanche card) {
        super(card);
    }

    @Override
    public Avalanche copy() {
        return new Avalanche(this);
    }
}
