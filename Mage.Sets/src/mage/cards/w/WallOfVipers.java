
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class WallOfVipers extends CardImpl {

    public WallOfVipers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {3}: Destroy Wall of Vipers and target creature it's blocking. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroySourceEffect(), new ManaCostsImpl("{3}"));
        ability.addEffect(new DestroyTargetEffect(" and target creature it's blocking"));
        ability.addTarget(new TargetCreaturePermanent(new WallOfVipersFilter()));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private WallOfVipers(final WallOfVipers card) {
        super(card);
    }

    @Override
    public WallOfVipers copy() {
        return new WallOfVipers(this);
    }
}

class WallOfVipersFilter extends FilterCreaturePermanent {
    
    public WallOfVipersFilter() {
        super("creature {this} is blocking");
    }
    
    public WallOfVipersFilter(final WallOfVipersFilter filter) {
        super(filter);
    }
    
    @Override
    public WallOfVipersFilter copy() {
        return new WallOfVipersFilter(this);
    }
    
    @Override
    public boolean match(Permanent permanent, UUID playerId, Ability source, Game game) {
        if (super.match(permanent, playerId, source, game)) {
            SubType subtype = (SubType) game.getState().getValue(source.getSourceId() + "_type");
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().contains(source.getSourceId()) && combatGroup.getAttackers().contains(permanent.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
