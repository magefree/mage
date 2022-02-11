
package mage.cards.p;

import java.util.LinkedHashSet;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class PalaceJailer extends CardImpl {

    public PalaceJailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Palace Jailer enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // When Palace Jailer enters the battlefield, exile target creature an opponent controls until an opponent becomes the monarch. (That creature returns under its owner's control.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new PalaceJailerExileEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private PalaceJailer(final PalaceJailer card) {
        super(card);
    }

    @Override
    public PalaceJailer copy() {
        return new PalaceJailer(this);
    }
}

class PalaceJailerExileEffect extends OneShotEffect {

    public PalaceJailerExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until an opponent becomes the monarch. <i>(That creature returns under its owner's control.)</i>";
    }

    public PalaceJailerExileEffect(final PalaceJailerExileEffect effect) {
        super(effect);
    }

    @Override
    public PalaceJailerExileEffect copy() {
        return new PalaceJailerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), sourceObject.getIdName()).apply(game, source);
        }
        return false;
    }
}

class OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility extends DelayedTriggeredAbility {

    public OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility() {
        super(new PalaceJailerReturnExiledPermanentsEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility(final OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility ability) {
        super(ability);
    }

    @Override
    public OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility copy() {
        return new OnOpponentBecomesMonarchReturnExiledToBattlefieldAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONARCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getPlayerId());
    }
}

class PalaceJailerReturnExiledPermanentsEffect extends OneShotEffect {

    public PalaceJailerReturnExiledPermanentsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled creature";
    }

    public PalaceJailerReturnExiledPermanentsEffect(final PalaceJailerReturnExiledPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public PalaceJailerReturnExiledPermanentsEffect copy() {
        return new PalaceJailerReturnExiledPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZone != null) {
                ExileZone exile = game.getExile().getExileZone(exileZone);
                if (exile != null) {
                    controller.moveCards(new LinkedHashSet<>(exile.getCards(game)), Zone.BATTLEFIELD, source, game, false, false, true, null);
                }
                return true;
            }
        }
        return false;
    }
}
