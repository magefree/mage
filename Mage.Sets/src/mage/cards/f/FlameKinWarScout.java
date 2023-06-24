
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class FlameKinWarScout extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public FlameKinWarScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When another creature enters the battlefield, sacrifice Flame-Kin War Scout. If you do, Flame-Kin War Scout deals 4 damage to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new FlameKinWarScourEffect(), filter, false, SetTargetPointer.PERMANENT, null));

    }

    private FlameKinWarScout(final FlameKinWarScout card) {
        super(card);
    }

    @Override
    public FlameKinWarScout copy() {
        return new FlameKinWarScout(this);
    }
}

class FlameKinWarScourEffect extends OneShotEffect {

    FlameKinWarScourEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "sacrifice {this}. If you do, {this} deals 4 damage to that creature.";
    }

    FlameKinWarScourEffect(final FlameKinWarScourEffect effect) {
        super(effect);
    }

    @Override
    public FlameKinWarScourEffect copy() {
        return new FlameKinWarScourEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            if (permanent.sacrifice(source, game)) {
                Effect effect = new DamageTargetEffect(4).setText("{this} deals 4 damage to it");
                effect.setTargetPointer(this.getTargetPointer());
                return effect.apply(game, source);
            }
        }
        return false;
    }
}
