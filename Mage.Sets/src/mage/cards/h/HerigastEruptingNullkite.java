package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileHandCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HerigastEruptingNullkite extends CardImpl {

    public HerigastEruptingNullkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Emerge {6}{R}{R}
        this.addAbility(new EmergeAbility(this, "{6}{R}{R}"));

        // When you cast this spell, you may exile your hand. If you do, draw three cards.
        this.addAbility(new CastSourceTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(3),
                new ExileHandCost()
        )));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature spell you cast has emerge. The emerge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(new HerigastEruptingNullkiteEffect()));
    }

    private HerigastEruptingNullkite(final HerigastEruptingNullkite card) {
        super(card);
    }

    @Override
    public HerigastEruptingNullkite copy() {
        return new HerigastEruptingNullkite(this);
    }
}

class HerigastEruptingNullkiteEffect extends ContinuousEffectImpl {

    HerigastEruptingNullkiteEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each creature spell you cast has emerge. The emerge cost is equal to its mana cost.";
    }

    private HerigastEruptingNullkiteEffect(final HerigastEruptingNullkiteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToGainEmerge = new HashSet<>();
        cardsToGainEmerge.addAll(controller.getHand().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        cardsToGainEmerge.addAll(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        controller.getLibrary().getCards(game).stream()
                .filter(c -> StaticFilters.FILTER_CARD_CREATURE.match(c, game))
                .forEach(cardsToGainEmerge::add);
        game.getExile().getAllCardsByRange(game, controller.getId()).stream()
                .filter(c -> StaticFilters.FILTER_CARD_CREATURE.match(c, game))
                .forEach(cardsToGainEmerge::add);
        game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY)
                .stream()
                .filter(card -> StaticFilters.FILTER_CARD_CREATURE.match(card, game))
                .forEach(cardsToGainEmerge::add);
        game.getStack().stream()
                .filter(Spell.class::isInstance)
                .filter(s -> s.isControlledBy(controller.getId()))
                .filter(s -> StaticFilters.FILTER_CARD_CREATURE.match((Spell) s, game))
                .map(s -> game.getCard(s.getSourceId()))
                .filter(Objects::nonNull)
                .forEach(cardsToGainEmerge::add);
        for (Card card : cardsToGainEmerge) {
            if (card.getManaCost().getText().isEmpty()) {
                continue; // card must have a mana cost
            }
            Ability ability = new EmergeAbility(card, card.getManaCost().getText());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getControllerOrOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }

    @Override
    public HerigastEruptingNullkiteEffect copy() {
        return new HerigastEruptingNullkiteEffect(this);
    }
}
