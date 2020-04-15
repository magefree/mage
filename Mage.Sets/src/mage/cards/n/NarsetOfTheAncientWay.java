package mage.cards.n;

import mage.ConditionalMana;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.emblems.NarsetOfTheAncientWayEmblem;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetOfTheAncientWay extends CardImpl {

    public NarsetOfTheAncientWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NARSET);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: You gain 2 life. Add {U}, {R}, or {W}. Spend this mana only to cast a noncreature spell.
        this.addAbility(new LoyaltyAbility(new NarsetOfTheAncientWayManaEffect(), 1));

        // −2: Draw a card, then you may discard a card. When you discard a nonland card this way, Narset of the Ancient Way deals damage equal to that card's converted mana cost to target creature or planeswalker.
        this.addAbility(new LoyaltyAbility(new NarsetOfTheAncientWayDrawEffect(), -2));

        // −6: You get an emblem with "Whenever you cast a noncreature spell, this emblem deals 2 damage to any target."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new NarsetOfTheAncientWayEmblem()), -6));
    }

    private NarsetOfTheAncientWay(final NarsetOfTheAncientWay card) {
        super(card);
    }

    @Override
    public NarsetOfTheAncientWay copy() {
        return new NarsetOfTheAncientWay(this);
    }
}

class NarsetOfTheAncientWayManaEffect extends OneShotEffect {

    NarsetOfTheAncientWayManaEffect() {
        super(Outcome.Benefit);
        staticText = "You gain 2 life. Add {U}, {R}, or {W}. Spend this mana only to cast a noncreature spell.";
    }

    private NarsetOfTheAncientWayManaEffect(final NarsetOfTheAncientWayManaEffect effect) {
        super(effect);
    }

    @Override
    public NarsetOfTheAncientWayManaEffect copy() {
        return new NarsetOfTheAncientWayManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.gainLife(2, game, source);
        ChoiceColor choice = new ChoiceColor();
        choice.getChoices().remove("Green");
        choice.getChoices().remove("Black");
        player.choose(Outcome.PutManaInPool, choice, game);
        ConditionalMana mana = new ConditionalMana(choice.getMana(1));
        mana.addCondition(new NarsetOfTheAncientWayManaCondition());
        player.getManaPool().addMana(mana, game, source);
        return true;
    }
}

class NarsetOfTheAncientWayManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source.getSourceId());
        return object != null && !object.isCreature();
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}

class NarsetOfTheAncientWayDrawEffect extends OneShotEffect {

    NarsetOfTheAncientWayDrawEffect() {
        super(Outcome.Benefit);
        staticText = "Draw a card, then you may discard a card. When you discard a nonland card this way, " +
                "{this} deals damage equal to that card's converted mana cost to target creature or planeswalker.";
    }

    private NarsetOfTheAncientWayDrawEffect(final NarsetOfTheAncientWayDrawEffect effect) {
        super(effect);
    }

    @Override
    public NarsetOfTheAncientWayDrawEffect copy() {
        return new NarsetOfTheAncientWayDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, game);
        if (player.getHand().isEmpty() || !player.chooseUse(Outcome.Discard, "Discard a card?", source, game)) {
            return false;
        }
        Card card = player.discardOne(false, source, game);
        if (card == null || card.isCreature()) {
            return false;
        }
        game.addDelayedTriggeredAbility(new NarsetOfTheAncientWayReflexiveTriggeredAbility(card.getConvertedManaCost()), source);
        return new SendOptionUsedEventEffect().apply(game, source);
    }
}

class NarsetOfTheAncientWayReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    NarsetOfTheAncientWayReflexiveTriggeredAbility(int cmc) {
        super(new DamageTargetEffect(cmc), Duration.OneUse, true);
        this.addTarget(new TargetCreatureOrPlaneswalker());
    }

    private NarsetOfTheAncientWayReflexiveTriggeredAbility(final NarsetOfTheAncientWayReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NarsetOfTheAncientWayReflexiveTriggeredAbility copy() {
        return new NarsetOfTheAncientWayReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "{this} deals damage to target creature or planeswalker " +
                "equal to the discarded card's converted mana cost";
    }
}
