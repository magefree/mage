package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileHandCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.EmergeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author grimreap124
 */
public final class HerigastEruptingNullkite extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("creature spell");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

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
        this.addAbility(new CastSourceTriggeredAbility(new DoIfCostPaid(new DrawCardSourceControllerEffect(3), new ExileHandCost()), false));
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Each creature spell you cast has emerge. The emerge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                (new HerigastEruptingNullkiteEffect())));
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
        staticText = "has emerge. The emerge cost is equal to its mana cost";

    }

    private HerigastEruptingNullkiteEffect(final HerigastEruptingNullkiteEffect effect) {
        super(effect);
    }

    @Override
    public HerigastEruptingNullkiteEffect copy() {
        return new HerigastEruptingNullkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Ability ability;
        for (Card card : game.getExile().getAllCardsByRange(game, source.getControllerId())) {
            if (StaticFilters.FILTER_CARD_NON_LAND.match(card, game)) {
                ability = new EmergeAbility(card, card.getManaCost());
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getLibrary().getCards(game)) {
            if (StaticFilters.FILTER_CARD_NON_LAND.match(card, game)) {
                ability = new EmergeAbility(card, card.getManaCost());
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (StaticFilters.FILTER_CARD_NON_LAND.match(card, game)) {
                ability = new EmergeAbility(card, card.getManaCost());
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            if (StaticFilters.FILTER_CARD_NON_LAND.match(card, game)) {
                ability = new EmergeAbility(card, card.getManaCost());
                game.getState().addOtherAbility(card, ability);
            }
        }
        game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY)
                .stream()
                .filter(card -> StaticFilters.FILTER_CARD_NON_LAND.match(card, game))
                .forEach(card -> {
                    Ability cAbility = new EmergeAbility(card, card.getManaCost());
                    game.getState().addOtherAbility(card, cAbility);});

        return true;
    }
}