
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Gridlock extends CardImpl {

    public Gridlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Tap X target nonland permanents.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target nonland permanents"));
        this.getSpellAbility().setTargetAdjuster(GridlockAdjuster.instance);
    }

    private Gridlock(final Gridlock card) {
        super(card);
    }

    @Override
    public Gridlock copy() {
        return new Gridlock(this);
    }
}

enum GridlockAdjuster implements TargetAdjuster {
    instance;
    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents");

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(ability.getManaCostsToPay().getX(), filter));
    }
}