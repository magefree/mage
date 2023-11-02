
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class Camel extends CardImpl {

    public Camel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // As long as Camel is attacking, prevent all damage Deserts would deal to Camel and to creatures banded with Camel.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CamelEffect()));

    }

    private Camel(final Camel card) {
        super(card);
    }

    @Override
    public Camel copy() {
        return new Camel(this);
    }
}

class CamelEffect extends PreventionEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent("Deserts");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    CamelEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "As long as {this} is attacking, prevent all damage Deserts would deal to {this} and to creatures banded with {this}";
    }

    private CamelEffect(final CamelEffect effect) {
        super(effect);
    }

    @Override
    public CamelEffect copy() {
        return new CamelEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0) {
            DamageEvent damageEvent = (DamageEvent) event;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null 
                    && sourcePermanent.isAttacking()
                    && (event.getTargetId().equals(source.getSourceId()) || sourcePermanent.getBandedCards().contains(event.getTargetId()))) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
