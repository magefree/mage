
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class WallOfVapor extends CardImpl {

    public WallOfVapor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Prevent all damage that would be dealt to Wall of Vapor by creatures it's blocking.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WallOfVaporEffect()));
    }

    private WallOfVapor(final WallOfVapor card) {
        super(card);
    }

    @Override
    public WallOfVapor copy() {
        return new WallOfVapor(this);
    }
}

class WallOfVaporEffect extends PreventionEffectImpl {

    WallOfVaporEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to Wall of Vapor by creatures it's blocking";
    }

    WallOfVaporEffect(final WallOfVaporEffect effect) {
        super(effect);
    }

    @Override
    public WallOfVaporEffect copy() {
        return new WallOfVaporEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new BlockedByIdPredicate(source.getSourceId()));
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
