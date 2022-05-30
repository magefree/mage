package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaezelVlaakithsChampion extends CardImpl {

    public LaezelVlaakithsChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If you would put one or more counters on a creature or planeswalker you control or on yourself, put that many plus one of each of those kinds of counters on that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new LaezelVlaakithsChampionEffect()));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private LaezelVlaakithsChampion(final LaezelVlaakithsChampion card) {
        super(card);
    }

    @Override
    public LaezelVlaakithsChampion copy() {
        return new LaezelVlaakithsChampion(this);
    }
}

class LaezelVlaakithsChampionEffect extends ReplacementEffectImpl {

    LaezelVlaakithsChampionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if you would put one or more counters on a creature or planeswalker you control or on yourself, " +
                "put that many plus one of each of those kinds of counters on that permanent or player instead";
    }

    LaezelVlaakithsChampionEffect(final LaezelVlaakithsChampionEffect effect) {
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
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        if (source.isControlledBy(event.getTargetId())) {
            return true;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && (permanent.isCreature(game) || permanent.isPlaneswalker(game))
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LaezelVlaakithsChampionEffect copy() {
        return new LaezelVlaakithsChampionEffect(this);
    }
}
