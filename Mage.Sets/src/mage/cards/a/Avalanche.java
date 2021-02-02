
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Avalanche extends CardImpl {

    public Avalanche(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{2}{R}{R}");

        // Destroy X target snow lands.
        this.getSpellAbility().addEffect(new DestroyTargetEffect("Destroy X target snow lands"));
        this.getSpellAbility().setTargetAdjuster(AvalancheAdjuster.instance);
    }

    private Avalanche(final Avalanche card) {
        super(card);
    }

    @Override
    public Avalanche copy() {
        return new Avalanche(this);
    }
}

enum AvalancheAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterLandPermanent("snow lands");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetPermanent(xValue, xValue, filter, false));
    }
}