package mage.cards.n;

import mage.ConditionalMana;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.command.emblems.NarsetOfTheAncientWayEmblem;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetOfTheAncientWay extends CardImpl {

    public NarsetOfTheAncientWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NARSET);
        this.setStartingLoyalty(4);

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
        MageObject object = game.getObject(source);
        return object != null && !object.isCreature(game);
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
                "{this} deals damage equal to that card's mana value to target creature or planeswalker.";
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
        player.drawCards(1, source, game);
        if (player.getHand().isEmpty() || !player.chooseUse(Outcome.Discard, "Discard a card?", source, game)) {
            return false;
        }
        Card card = player.discardOne(false, false, source, game);
        if (card == null || card.isLand(game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(card.getManaValue()), false, "{this} deals damage " +
                "to target creature or planeswalker equal to the discarded card's mana value"
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
