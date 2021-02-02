package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
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

    private PirImaginativeRascal(final PirImaginativeRascal card) {
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
        event.setAmountForCounters(event.getAmount() + 1, true);
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
        return permanent != null && player != null && event.getAmount() > 0
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
