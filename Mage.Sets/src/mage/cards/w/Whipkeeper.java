
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class Whipkeeper extends CardImpl {

    public Whipkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Whipkeeper deals damage to target creature equal to the damage already dealt to it this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WhipkeeperEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    private Whipkeeper(final Whipkeeper card) {
        super(card);
    }

    @Override
    public Whipkeeper copy() {
        return new Whipkeeper(this);
    }
}

class WhipkeeperEffect extends OneShotEffect {

    public WhipkeeperEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to target creature equal to the damage already dealt to it this turn.";
    }
    private WhipkeeperEffect(final WhipkeeperEffect effect) {
        super(effect);
    }
    
    @Override
    public WhipkeeperEffect copy() {
        return new WhipkeeperEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.damage(creature.getDamage(), source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }
    
}