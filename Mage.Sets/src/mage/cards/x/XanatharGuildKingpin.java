package mage.cards.x;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author raphael-schulz
 */
public final class XanatharGuildKingpin extends CardImpl {

    public XanatharGuildKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEHOLDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, choose target opponent. Until end of turn, that player can’t cast spells, you may look at the top card of their library any time, you may play the top card of their library, and you may spend mana as though it were mana of any color to cast spells this way.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new XanatharGuildKingpinRuleModifyingEffect()
                .setText("choose target opponent. Until end of turn, that player can't cast spells,"),
                TargetController.YOU, false
        );
        ability.addEffect(new XanatharLookAtTopCardOfLibraryEffect()
                .setText(" you may look at the top card of their library any time,"));
        ability.addEffect(new XanatharPlayFromTopOfTargetLibraryEffect()
                .setText(" you may play the top card of their library,"));
        ability.addEffect(new XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect()
                .setText(" and you may spend mana as thought it were mana of any color to cast spells this way"));
        ability.addCustomOutcome(Outcome.PreventCast);
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

    XanatharGuildKingpinRuleModifyingEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
    }

    private XanatharGuildKingpinRuleModifyingEffect(final XanatharGuildKingpinRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public XanatharGuildKingpinRuleModifyingEffect copy() {
        return new XanatharGuildKingpinRuleModifyingEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject mageObject = game.getObject(source);
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

class XanatharLookAtTopCardOfLibraryEffect extends ContinuousEffectImpl {

    XanatharLookAtTopCardOfLibraryEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    private XanatharLookAtTopCardOfLibraryEffect(final XanatharLookAtTopCardOfLibraryEffect effect) {
        super(effect);
    }

    @Override
    public XanatharLookAtTopCardOfLibraryEffect copy() {
        return new XanatharLookAtTopCardOfLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.inCheckPlayableState()) { // Ignored - see https://github.com/magefree/mage/issues/6994
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        if (!canLookAtNextTopLibraryCard(game)) {
            return false;
        }
        Card topCard = opponent.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of " + opponent.getName() + "'s library", topCard, game);
        return true;
    }

}

class XanatharPlayFromTopOfTargetLibraryEffect extends AsThoughEffectImpl {

    XanatharPlayFromTopOfTargetLibraryEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private XanatharPlayFromTopOfTargetLibraryEffect(final XanatharPlayFromTopOfTargetLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public XanatharPlayFromTopOfTargetLibraryEffect copy() {
        return new XanatharPlayFromTopOfTargetLibraryEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }
        if (affectedAbility instanceof SpellAbility) {
            SpellAbility spell = (SpellAbility) affectedAbility;
            cardToCheck = spell.getCharacteristics(game);
            if (spell.getManaCosts().isEmpty()) {
                return false;  // prevent casting cards without mana cost?
            }
        }
        // only permits you to cast
        if (!playerId.equals(source.getControllerId())) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        // main card of spell/land must be on top of the opponent's library
        Card topCard = opponent.getLibrary().getFromTop(game);
        return topCard != null && topCard.getId().equals(cardToCheck.getMainCard().getId());
    }
}

class XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect extends OneShotEffect {

    XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect() {
        super(Outcome.Benefit);
    }

    private XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect(final XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card topCard = game.getPlayer(source.getFirstTarget()).getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        int zcc = game.getState().getZoneChangeCounter(topCard.getId());
        game.addEffect(new SpendManaAsAnyColorToCastTopOfLibraryTargetEffect().setTargetPointer(new FixedTarget(topCard.getId(), zcc)), source);
        return true;
    }

    @Override
    public XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect copy() {
        return new XanatharGuildKingpinSpendManaAsAnyColorOneShotEffect(this);
    }

}

class SpendManaAsAnyColorToCastTopOfLibraryTargetEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public SpendManaAsAnyColorToCastTopOfLibraryTargetEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
    }

    private SpendManaAsAnyColorToCastTopOfLibraryTargetEffect(final SpendManaAsAnyColorToCastTopOfLibraryTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SpendManaAsAnyColorToCastTopOfLibraryTargetEffect copy() {
        return new SpendManaAsAnyColorToCastTopOfLibraryTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        UUID targetId = CardUtil.getMainCardId(game, fixedTarget.getTarget());

        Card topCard = game.getPlayer(source.getFirstTarget()).getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }

        // If top card of target opponent's library changed, discard the current ContinuousEffect and create a new one
        if (!topCard.getId().equals(targetId) && canLookAtNextTopLibraryCard(game) && !this.isDiscarded()) {
            int zcc = game.getState().getZoneChangeCounter(topCard.getId());
            game.addEffect(new SpendManaAsAnyColorToCastTopOfLibraryTargetEffect().setTargetPointer(new FixedTarget(topCard.getId(), zcc)), source);
            this.discard();
        }
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, targetId)
                && game.getState().getZoneChangeCounter(objectId) <= fixedTarget.getZoneChangeCounter() + 1
                && (game.getState().getZone(objectId) == Zone.STACK || game.getState().getZone(objectId) == Zone.LIBRARY);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
