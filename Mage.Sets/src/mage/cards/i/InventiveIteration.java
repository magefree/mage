package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InventiveIteration extends TransformingDoubleFacedCard {

    public InventiveIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{3}{U}",
                "Living Breakthrough",
                new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.MOONFOLK}, "U"
        );

        // Inventive Iteration
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I — Return up to one target creature or planeswalker to its owner's hand.
        sagaAbility.addChapterEffect(
                this.getLeftHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I,
                new ReturnToHandTargetEffect(), new TargetCreatureOrPlaneswalker(0, 1)
        );

        // II — Return an artifact card from your graveyard to your hand. If you can't, draw a card.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new InventiveIterationEffect());

        // III — Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, new ExileSagaAndReturnTransformedEffect());

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Living Breakthrough
        this.getRightHalfCard().setPT(3, 3);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell, your opponents can't cast spells with the same mana value as that spell until your next turn.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new LivingBreakthroughEffect(), false));
    }

    private InventiveIteration(final InventiveIteration card) {
        super(card);
    }

    @Override
    public InventiveIteration copy() {
        return new InventiveIteration(this);
    }
}

class InventiveIterationEffect extends OneShotEffect {

    InventiveIterationEffect() {
        super(Outcome.Benefit);
        staticText = "return an artifact card from your graveyard to your hand. If you can't, draw a card";
    }

    private InventiveIterationEffect(final InventiveIterationEffect effect) {
        super(effect);
    }

    @Override
    public InventiveIterationEffect copy() {
        return new InventiveIterationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card;
        switch (player.getGraveyard().count(StaticFilters.FILTER_CARD_ARTIFACT, game)) {
            case 0:
                player.drawCards(1, source, game);
                return true;
            case 1:
                card = RandomUtil.randomFromCollection(
                        player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, game)
                );
                break;
            default:
                TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT);
                target.withNotTarget(true);
                player.choose(outcome, player.getGraveyard(), target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        player.moveCards(card, Zone.HAND, source, game);
        return true;
    }
}

class LivingBreakthroughEffect extends ContinuousRuleModifyingEffectImpl {

    private int manaValue = -1;

    LivingBreakthroughEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "your opponents can't cast spells with the same mana value as that spell until your next turn";
    }

    private LivingBreakthroughEffect(final LivingBreakthroughEffect effect) {
        super(effect);
        this.manaValue = effect.manaValue;
    }

    @Override
    public LivingBreakthroughEffect copy() {
        return new LivingBreakthroughEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            this.manaValue = spell.getManaValue();
        }
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells with mana value " + manaValue
                    + " this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getManaValue() == this.manaValue;
    }
}
