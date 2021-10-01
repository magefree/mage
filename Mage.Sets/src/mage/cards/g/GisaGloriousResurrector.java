package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DecayedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GisaGloriousResurrector extends CardImpl {

    public GisaGloriousResurrector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // If a creature an opponent controls would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GisaGloriousResurrectorExileEffect()));

        // At the beginning of your upkeep, put all creature cards exiled with Gisa, Glorious Resurrector onto the battlefield under your control. They gain decayed.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new GisaGloriousResurrectorReturnEffect(), TargetController.YOU, false
        ));
    }

    private GisaGloriousResurrector(final GisaGloriousResurrector card) {
        super(card);
    }

    @Override
    public GisaGloriousResurrector copy() {
        return new GisaGloriousResurrector(this);
    }
}

class GisaGloriousResurrectorExileEffect extends ReplacementEffectImpl {

    GisaGloriousResurrectorExileEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if a creature an opponent controls would die, exile it instead";
    }

    private GisaGloriousResurrectorExileEffect(final GisaGloriousResurrectorExileEffect effect) {
        super(effect);
    }

    @Override
    public GisaGloriousResurrectorExileEffect copy() {
        return new GisaGloriousResurrectorExileEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTarget() instanceof PermanentToken) {
            return player.moveCards(zEvent.getTarget(), Zone.EXILED, source, game);
        }
        game.getState().setValue("GisaGloriousResurrectorExile"
                + source.getSourceId().toString()
                + game.getState().getZoneChangeCounter(source.getSourceId()), source);
        return player.moveCardsToExile(
                zEvent.getTarget(), source, game, false,
                CardUtil.getExileZoneId(game, source), "Gisa, Glorious Resurrector"
        );
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && game.getOpponents(zEvent.getTarget().getControllerId()).contains(source.getControllerId());
    }
}

class GisaGloriousResurrectorReturnEffect extends OneShotEffect {

    GisaGloriousResurrectorReturnEffect() {
        super(Outcome.Benefit);
        staticText = "put all creature cards exiled with {this} "
                + "onto the battlefield under your control. They gain decayed";
    }

    private GisaGloriousResurrectorReturnEffect(final GisaGloriousResurrectorReturnEffect effect) {
        super(effect);
    }

    @Override
    public GisaGloriousResurrectorReturnEffect copy() {
        return new GisaGloriousResurrectorReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Ability exiledWithSource = (Ability) game.getState().getValue("GisaGloriousResurrectorExile"
                + source.getSourceId().toString()
                + game.getState().getZoneChangeCounter(source.getSourceId()));
        if (exiledWithSource == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, exiledWithSource));
        if (player == null
                || exileZone == null
                || exileZone.isEmpty()) {
            return false;
        }
        Cards cards = new CardsImpl(exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.BATTLEFIELD, game);
        if (cards.isEmpty()) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                new DecayedAbility(), Duration.Custom
        ).setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }
}
