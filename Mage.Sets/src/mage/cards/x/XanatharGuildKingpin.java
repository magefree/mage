package mage.cards.x;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author raphael-schulz
 */
public final class XanatharGuildKingpin extends CardImpl {

    public XanatharGuildKingpin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEHOLDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, choose target opponent. Until end of turn, that player can’t cast spells, you may look at the top card of their library any time, you may play the top card of their library, and you may spend mana as though it were mana of any color to cast spells this way.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new XanatharGuildKingpinRuleModifyingEffect(),
                TargetController.YOU, false
        );
        ability.addEffect(new XanatharGuildKingpinTopOfOpponentLibraryTargetEffect());
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
        staticText = "choose target opponent";
    }

    private XanatharGuildKingpinRuleModifyingEffect(final XanatharGuildKingpinRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public XanatharGuildKingpinRuleModifyingEffect copy() {
        return new XanatharGuildKingpinRuleModifyingEffect(this);
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

class XanatharGuildKingpinTopOfOpponentLibraryTargetEffect extends ContinuousEffectImpl {

    public XanatharGuildKingpinTopOfOpponentLibraryTargetEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, that player can’t cast spells, you may look at the top card of their library any time, you may play the top card of their library," +
                " and you may spend mana as though it were mana of any color to cast spells this way.";
    }

    private XanatharGuildKingpinTopOfOpponentLibraryTargetEffect(final XanatharGuildKingpinTopOfOpponentLibraryTargetEffect effect) {
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
        CardUtil.makeCardPlayable(game, source, topCard, Duration.EndOfTurn, true);
        return true;
    }

    @Override
    public XanatharGuildKingpinTopOfOpponentLibraryTargetEffect copy() {
        return new XanatharGuildKingpinTopOfOpponentLibraryTargetEffect(this);
    }
}


