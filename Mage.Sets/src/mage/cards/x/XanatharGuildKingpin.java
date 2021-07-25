
package mage.cards.x;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.*;
import mage.abilities.effects.common.continuous.PlayTheTopCardOfTargetPlayerLibraryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author raphael-schulz
 */
public final class XanatharGuildKingpin extends CardImpl {

    public XanatharGuildKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEHOLDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new XanatharGuildKingpinRuleModifyingEffect(),
                TargetController.YOU, false
        );
        ability.addEffect(new XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect());
        ability.addEffect(new PlayTheTopCardOfTargetPlayerLibraryEffect(new FilterCard("play cards"), true));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);


    }

    private XanatharGuildKingpin(final XanatharGuildKingpin card) {
        super(card);
    }

    @Override
    public XanatharGuildKingpin copy() {
        return new XanatharGuildKingpin(this);
    }
}

class XanatharGuildKingpinRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    public XanatharGuildKingpinRuleModifyingEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "choose target opponent. This turn, that player can't cast spells";
    }

    private XanatharGuildKingpinRuleModifyingEffect(final mage.cards.x.XanatharGuildKingpinRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.x.XanatharGuildKingpinRuleModifyingEffect copy() {
        return new mage.cards.x.XanatharGuildKingpinRuleModifyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject mageObject = game.getObject(source.getSourceId());
        if (targetPlayer != null && mageObject != null) {
            return "This turn you can't cast spells" +
                    " (" + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(getTargetPointer().getFirst(game, source));
    }
}

class XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect extends ContinuousEffectImpl {

    public XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, you may look at the top card of target player's library any time.";
    }

    private XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect(final mage.cards.x.XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }

        Card topCard = targetPlayer.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        if (!canLookAtNextTopLibraryCard(game)) {
            return false;
        }
        controller.lookAtCards("Top card of " + targetPlayer.getName() + "'s library", topCard, game);

        ContinuousEffect effect = new XanatharGuildKingpinSpendManaAsThoughEffect(controller.getId());
        effect.setTargetPointer(new FixedTarget(topCard.getId(), game));
        game.addEffect(effect, source);
        return true;
    }

    @Override
    public mage.cards.x.XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect copy() {
        return new mage.cards.x.XanatharGuildKingpinLookAtTopCardOfTargetPlayerLibraryAnyTimeEffect(this);
    }
}

class XanatharGuildKingpinSpendManaAsThoughEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final UUID authorizedPlayerId;

    XanatharGuildKingpinSpendManaAsThoughEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "You may spend mana as though it were mana of any color";
    }

    private XanatharGuildKingpinSpendManaAsThoughEffect(mage.cards.x.XanatharGuildKingpinSpendManaAsThoughEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public mage.cards.x.XanatharGuildKingpinSpendManaAsThoughEffect copy() {
        return new mage.cards.x.XanatharGuildKingpinSpendManaAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {

        //Same as for ThiefOfSanity
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (objectId.equals(((FixedTarget) getTargetPointer()).getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1) {
            // if the card moved from top of library to spell the zone change counter is increased by 1 (effect must apply before and on stack, use isCheckPlayableMode?)
            return affectedControllerId.equals(authorizedPlayerId);
        } else if (((FixedTarget) getTargetPointer()).getTarget().equals(objectId)) {
            // object has moved zone so effect can be discarded
            this.discard();
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
