
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class PirImaginativeRascal extends CardImpl {

    public PirImaginativeRascal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Partner with Toothy, Imaginary Friend (When this creature enters the battlefield, target player may put Toothy into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Toothy, Imaginary Friend", true));

        // If one or more counters would be put on a permanent your team controls, that many plus one of each of those kinds of counters are put on that permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PirImaginativeRascalEffect()));
    }

    public PirImaginativeRascal(final PirImaginativeRascal card) {
        super(card);
    }

    @Override
    public PirImaginativeRascal copy() {
        return new PirImaginativeRascal(this);
    }
}

class PirImaginativeRascalEffect extends ReplacementEffectImpl {

    PirImaginativeRascalEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "If one or more counters would be put on a permanent your team controls, "
                + "that many plus one of each of those kinds of counters are put on that permanent instead";
    }

    PirImaginativeRascalEffect(final PirImaginativeRascalEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        if (amount >= 1) {
            event.setAmount(amount + 1);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && player != null
                && !player.hasOpponent(permanent.getControllerId(), game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PirImaginativeRascalEffect copy() {
        return new PirImaginativeRascalEffect(this);
    }
}
