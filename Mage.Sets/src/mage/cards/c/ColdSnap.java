
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ColdSnap extends CardImpl {

    public ColdSnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));
        
        // At the beginning of each player's upkeep, Cold Snap deals damage to that player equal to the number of snow lands he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ColdSnapDamageTargetEffect(), TargetController.ANY, false, true));
    }

    public ColdSnap(final ColdSnap card) {
        super(card);
    }

    @Override
    public ColdSnap copy() {
        return new ColdSnap(this);
    }
}

class ColdSnapDamageTargetEffect extends OneShotEffect{
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("snow lands");

    static {
        filter.add(new SupertypePredicate(SuperType.SNOW));
    }
    
    public ColdSnapDamageTargetEffect()
    {
        super(Outcome.Damage);
    }
    
    public ColdSnapDamageTargetEffect(ColdSnapDamageTargetEffect copy)
    {
        super(copy);
    }
        
    @Override
    public String getText(Mode mode) {
        return "{this} deals damage to that player equal to the number of snow lands he or she controls";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game).size();
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public ColdSnapDamageTargetEffect copy() {
        return new ColdSnapDamageTargetEffect(this);
    }
}
