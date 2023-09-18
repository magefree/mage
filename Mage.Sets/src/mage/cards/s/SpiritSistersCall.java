package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class SpiritSistersCall extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("permanent card in your graveyard");

    public SpiritSistersCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{B}");

        // At the beginning of your end step, choose target permanent card in your graveyard.
        // You may sacrifice a permanent that shares a card type with the chosen card.
        // If you do, return the chosen card from your graveyard to the battlefield and it gains "If this permanent would leave the battlefield, exile it instead of putting it anywhere else."
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new SpiritSistersCallDoIfEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SpiritSistersCall(final SpiritSistersCall card) {
        super(card);
    }

    @Override
    public SpiritSistersCall copy() {
        return new SpiritSistersCall(this);
    }
}

class SpiritSistersCallDoIfEffect extends OneShotEffect {

    public SpiritSistersCallDoIfEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "choose target permanent card in your graveyard. " +
                "You may sacrifice a permanent that shares a card type with the chosen card. " +
                "If you do, return the chosen card from your graveyard to the battlefield and it gains \"If this permanent would leave the battlefield, exile it instead of putting it anywhere else.\"";
    }

    private SpiritSistersCallDoIfEffect(final SpiritSistersCallDoIfEffect effect) {
        super(effect);
    }

    @Override
    public SpiritSistersCallDoIfEffect copy() {
        return new SpiritSistersCallDoIfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        Card card = game.getCard(targetId);
        if (card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
            return false;
        }
        SharesCardTypePredicate predicate = new SharesCardTypePredicate(card.getCardType(game));
        FilterControlledPermanent filter = new FilterControlledPermanent(predicate.toString());
        filter.add(predicate);
        return new DoIfCostPaid(new SpiritSistersCallReturnToBattlefieldEffect(), new SacrificeTargetCost(filter)).apply(game, source);
    }
}

class SpiritSistersCallReturnToBattlefieldEffect extends OneShotEffect {

    public SpiritSistersCallReturnToBattlefieldEffect() {
        super(Outcome.PutCardInPlay);
    }

    private SpiritSistersCallReturnToBattlefieldEffect(final SpiritSistersCallReturnToBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public SpiritSistersCallReturnToBattlefieldEffect copy() {
        return new SpiritSistersCallReturnToBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetId = source.getFirstTarget();
        Card card = game.getCard(targetId);
        if (controller == null || card == null || game.getState().getZone(targetId) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            ContinuousEffect effect = new GainAbilityTargetEffect(new SimpleStaticAbility(new SpiritSistersCallReplacementEffect()), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class SpiritSistersCallReplacementEffect extends ReplacementEffectImpl {

    public SpiritSistersCallReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If {this} would leave the battlefield, exile it instead of putting it anywhere else";
    }

    private SpiritSistersCallReplacementEffect(final SpiritSistersCallReplacementEffect effect) {
        super(effect);
    }

    @Override
    public SpiritSistersCallReplacementEffect copy() {
        return new SpiritSistersCallReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        UUID targetId = zEvent.getTargetId();
        return targetId != null && targetId.equals(source.getSourceId())
                && zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() != Zone.EXILED;
    }
}
