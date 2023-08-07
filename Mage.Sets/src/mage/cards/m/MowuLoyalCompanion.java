package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MowuLoyalCompanion extends CardImpl {

    public MowuLoyalCompanion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // If one or more +1/+1 counters would be put on Mowu, Loyal Companion, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new MowuLoyalCompanionEffect()));
    }

    private MowuLoyalCompanion(final MowuLoyalCompanion card) {
        super(card);
    }

    @Override
    public MowuLoyalCompanion copy() {
        return new MowuLoyalCompanion(this);
    }
}

class MowuLoyalCompanionEffect extends ReplacementEffectImpl {

    MowuLoyalCompanionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, true);
        staticText = "If one or more +1/+1 counters would be put on {this}, " +
                "that many plus one +1/+1 counters are put on it instead";
    }

    private MowuLoyalCompanionEffect(final MowuLoyalCompanionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowInc(event.getAmount(), 1), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.getId().equals(source.getSourceId())
                    && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MowuLoyalCompanionEffect copy() {
        return new MowuLoyalCompanionEffect(this);
    }
}
