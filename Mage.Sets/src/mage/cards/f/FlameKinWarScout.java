
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
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class FlameKinWarScout extends CardImpl {

    public FlameKinWarScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When another creature enters the battlefield, sacrifice Flame-Kin War Scout. If you do, Flame-Kin War Scout deals 4 damage to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            Zone.BATTLEFIELD, new FlameKinWarScourEffect(),
            StaticFilters.FILTER_ANOTHER_CREATURE, false,
            SetTargetPointer.PERMANENT, null
        ));

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
