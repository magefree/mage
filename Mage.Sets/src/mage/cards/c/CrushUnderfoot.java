
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CrushUnderfoot extends CardImpl {

    public CrushUnderfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.GIANT);


        // Choose a Giant creature you control. It deals damage equal to its power to target creature.
        this.getSpellAbility().addEffect(new CrushUnderfootEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature damage is dealt to")));

    }

    private CrushUnderfoot(final CrushUnderfoot card) {
        super(card);
    }

    @Override
    public CrushUnderfoot copy() {
        return new CrushUnderfoot(this);
    }
}

class CrushUnderfootEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Giant you control");
    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public CrushUnderfootEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a Giant creature you control. It deals damage equal to its power to target creature";
    }

    public CrushUnderfootEffect(final CrushUnderfootEffect effect) {
        super(effect);
    }

    @Override
    public CrushUnderfootEffect copy() {
        return new CrushUnderfootEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Choose a Giant creature you control (not targeted, happens during effect resolving )
            Target target = new TargetControlledCreaturePermanent(1,1, filter,false);
            if (target.canChoose(controller.getId(), source, game)
                    && controller.chooseTarget(outcome, target, source, game)) {
                Permanent giant = game.getPermanent(target.getFirstTarget());                
                if (giant != null) {
                    game.informPlayers("Crush Underfoot: Chosen Giant is " + giant.getName());
                    Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                    if (targetCreature != null) {
                        targetCreature.damage(giant.getPower().getValue(), source.getSourceId(), source, game, false, true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
