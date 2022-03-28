
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class TimberpackWolf extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature you control named Timberpack Wolf");

    static {
        filter.add(new NamePredicate("Timberpack Wolf"));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TimberpackWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Timberpack Wolf gets +1/+1 for each other creature you control named Timberpack Wolf.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TimberpackWolfEffect()));
    }

    private TimberpackWolf(final TimberpackWolf card) {
        super(card);
    }

    @Override
    public TimberpackWolf copy() {
        return new TimberpackWolf(this);
    }


    static class TimberpackWolfEffect extends ContinuousEffectImpl {

        public TimberpackWolfEffect() {
            super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
            staticText = "{this} gets +1/+1 for each other creature you control named Timberpack Wolf";
        }

        public TimberpackWolfEffect(final TimberpackWolfEffect effect) {
            super(effect);
        }

        @Override
        public TimberpackWolfEffect copy() {
            return new TimberpackWolfEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) - 1;
            if (count > 0) {
                Permanent target = game.getPermanent(source.getSourceId());
                if (target != null) {
                    target.addPower(count);
                    target.addToughness(count);
                    return true;
                }
            }
            return false;
        }
    }
}




