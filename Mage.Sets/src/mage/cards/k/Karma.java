
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public final class Karma extends CardImpl {


    
    public Karma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");


        // At the beginning of each player's upkeep, Karma deals damage to that player equal to the number of Swamps he or she controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new KarmaDamageTargetEffect(), TargetController.ANY, false));
    }

    public Karma(final Karma card) {
        super(card);
    }

    @Override
    public Karma copy() {
        return new Karma(this);
    }
}

class KarmaDamageTargetEffect extends OneShotEffect{
    
    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("Swamps");
    static{
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }
    
    public KarmaDamageTargetEffect()
    {
        super(Outcome.Damage);
    }
    
    public KarmaDamageTargetEffect(KarmaDamageTargetEffect copy)
    {
        super(copy);
    }
        
    @Override
    public String getText(Mode mode) {
        return "{this} deals damage to that player equal to the number of Swamps he or she controls";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = game.getBattlefield().getAllActivePermanents(filter,targetPointer.getFirst(game, source) , game).size();
            player.damage(damage, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public KarmaDamageTargetEffect copy() {
        return new KarmaDamageTargetEffect(this);
    }
}
