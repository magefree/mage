package mage.cards.t;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.abilities.Ability;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.MageObject;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.ExileZone;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author @stwalsh4118
 */
public final class TheKenrithsRoyalFuneral extends CardImpl {

    public TheKenrithsRoyalFuneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");
        
        this.addSuperType(SuperType.LEGENDARY);

        // When The Kenriths' Royal Funeral enters the battlefield, exile up to two target legendary creature cards from your graveyard. You draw X cards and you lose X life, where X is the greatest mana value among cards exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TheKenrithsRoyalFuneralEffect().setText("exile up to two target legendary creature cards from your graveyard. You draw X cards and you lose X life, where X is the greatest mana value among cards exiled this way"));
        this.addAbility(ability);

        // Legendary spells you cast cost {1} less to cast for each card exiled with The Kenriths' Royal Funeral.
        this.addAbility(new SimpleStaticAbility(new TheKenrithsRoyalFuneralCostReductionEffect()));

    }

    private TheKenrithsRoyalFuneral(final TheKenrithsRoyalFuneral card) {
        super(card);
    }

    @Override
    public TheKenrithsRoyalFuneral copy() {
        return new TheKenrithsRoyalFuneral(this);
    }
}

class TheKenrithsRoyalFuneralEffect extends OneShotEffect {

    private static final FilterCreatureCard graveyardFilter = new FilterCreatureCard("two legendary creature cards from your graveyard");

    static {
        graveyardFilter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheKenrithsRoyalFuneralEffect() {
        super(Outcome.Exile);
    }

    private TheKenrithsRoyalFuneralEffect(final TheKenrithsRoyalFuneralEffect effect) {
        super(effect);
    }

    @Override
    public TheKenrithsRoyalFuneralEffect copy() {
        return new TheKenrithsRoyalFuneralEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInGraveyard target = new TargetCardInGraveyard(0,2, graveyardFilter);
            controller.choose(outcome, target, source, game);
            Cards cards = new CardsImpl();
            cards.addAll(target.getTargets());
            MageObject sourceObject = source.getSourceObject(game);
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            String exileName = sourceObject == null ? null : sourceObject.getIdName();
            int greatestValue = 0;
            for(Card card: cards.getCards(game)) {
                int value = card.getManaValue();
                if(value > greatestValue) {
                    greatestValue = value;
                }
                if(card != null) {
                    controller.moveCardsToExile(card, source, game, true, exileId, exileName);
                }

            }

            controller.drawCards(greatestValue, source, game);
            controller.loseLife(greatestValue, game, source, false);
            return true;
        }
        return false;
    }
}

class TheKenrithsRoyalFuneralCostReductionEffect extends CostModificationEffectImpl {

    public TheKenrithsRoyalFuneralCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Legendary spells you cast cost {1} less to cast for each card exiled with {this}";
    }

    private TheKenrithsRoyalFuneralCostReductionEffect(final TheKenrithsRoyalFuneralCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public TheKenrithsRoyalFuneralCostReductionEffect copy() {
        return new TheKenrithsRoyalFuneralCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (abilityToModify instanceof SpellAbility) {
            // sourceObjectZoneChangeCounter is not working here.  Getting it from GameState works.
            UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()));
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            Card castCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (exileZone != null && castCard != null && castCard.getSuperType().contains(SuperType.LEGENDARY)) {
                int amount = 0;
                for (UUID cardId : exileZone) {
                    amount++;
                }
                if (amount > 0) {
                    CardUtil.reduceCost(abilityToModify, amount);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.isControlledBy(source.getControllerId());
    }
}
