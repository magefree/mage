package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetImpl;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

/**
 *
 * @author grimreap124
 */
public final class GluttonousHellkite extends CardImpl {

    public GluttonousHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{X}{B}{R}{G}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When you cast this spell, each player sacrifices X creatures. Gluttonous Hellkite enters the battlefield with two +1/+1 counters on it for each creature sacrificed this way.
        Ability ability = new CastSourceTriggeredAbility(new GluttonousHellkiteEffect());
        ability.addEffect(new GluttonousHellkiteReplacementEffect());

        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

    }

    private GluttonousHellkite(final GluttonousHellkite card) {
        super(card);
    }

    @Override
    public GluttonousHellkite copy() {
        return new GluttonousHellkite(this);
    }
}

class GluttonousHellkiteEffect extends OneShotEffect {

    DynamicValue xValue;

    GluttonousHellkiteEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each player sacrifices X creatures";
    }

    private GluttonousHellkiteEffect(final GluttonousHellkiteEffect effect) {
        super(effect);
    }

    @Override
    public GluttonousHellkiteEffect copy() {
        return new GluttonousHellkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Permanent> sacPermanents = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        Object obj = getValue(CastSourceTriggeredAbility.SOURCE_CAST_SPELL_ABILITY);
        int sacrificeAmount = 0;
        if (controller == null ) {
            return false;
        }
        if (obj instanceof SpellAbility) {
            sacrificeAmount = ((SpellAbility) obj).getManaCostsToPay().getX();
        }
        if (sacrificeAmount < 1) {
            return true;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetSacrifice target = new TargetSacrifice(sacrificeAmount, StaticFilters.FILTER_PERMANENT_CREATURE);
                if (target.canChoose(player.getId(), source, game)) {
                    player.choose(Outcome.Sacrifice, target, source, game);
                    sacPermanents.add(game.getPermanent(target.getFirstTarget()));
                }
            }
        }
        for (Permanent permanent : sacPermanents) {
            if (permanent != null) {
                if (!permanent.sacrifice(source, game)) {
                    sacPermanents.remove(permanent);
                }
            }
        }


        for (Effect effect : source.getEffects()) {
            if (effect instanceof GluttonousHellkiteReplacementEffect) {
                effect.setValue("GluttonousHellkiteCounters", sacPermanents.size() * 2);
            }
        }
        return true;
    }
}

class GluttonousHellkiteReplacementEffect extends ReplacementEffectImpl {

    GluttonousHellkiteReplacementEffect() {
        super(Duration.OneUse, Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with two +1/+1 counters on it for each creature sacrificed this way";
    }

    private GluttonousHellkiteReplacementEffect(final GluttonousHellkiteReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Object object = this.getValue("GluttonousHellkiteCounters");

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (permanent != null && object instanceof Integer) {
            int amount = ((Integer) object);
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public GluttonousHellkiteReplacementEffect copy() {
        return new GluttonousHellkiteReplacementEffect(this);
    }

}