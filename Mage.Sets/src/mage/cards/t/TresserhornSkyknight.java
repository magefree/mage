
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class TresserhornSkyknight extends CardImpl {

    public TresserhornSkyknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all damage that would be dealt to Tresserhorn Skyknight by creatures with first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TresserhornSkyknightEffect()));
    }

    private TresserhornSkyknight(final TresserhornSkyknight card) {
        super(card);
    }

    @Override
    public TresserhornSkyknight copy() {
        return new TresserhornSkyknight(this);
    }
}

class TresserhornSkyknightEffect extends PreventionEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with first strike");

    static {
        filter.add(new AbilityPredicate(FirstStrikeAbility.class));
    }

    TresserhornSkyknightEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to Tresserhorn Skyknight by creatures with first strike";
    }

    TresserhornSkyknightEffect(final TresserhornSkyknightEffect effect) {
        super(effect);
    }

    @Override
    public TresserhornSkyknightEffect copy() {
        return new TresserhornSkyknightEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (event.getTargetId().equals(source.getSourceId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (permanent != null && filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
