
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class Gridlock extends CardImpl {
    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanents");

    public Gridlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}");


        // Tap X target nonland permanents.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        // Correct number of targets will be set in adjustTargets
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1,filter, false));
        
    }

    public Gridlock(final Gridlock card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberToTap = ability.getManaCostsToPay().getX();
            numberToTap = Math.min(game.getBattlefield().count(filter, ability.getSourceId(), ability.getControllerId(), game), numberToTap);
            ability.addTarget(new TargetPermanent(numberToTap, filter));
        }
    }

    @Override
    public Gridlock copy() {
        return new Gridlock(this);
    }
}
