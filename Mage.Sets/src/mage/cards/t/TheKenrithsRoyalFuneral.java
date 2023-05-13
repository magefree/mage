package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheKenrithsRoyalFuneral extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("legendary creature cards from your graveyard");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public TheKenrithsRoyalFuneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // When The Kenriths' Royal Funeral enters the battlefield, exile up to two target legendary creature cards from your graveyard. You draw X cards and you lose X life, where X is the greatest mana value among cards exiled this way.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TheKenrithsRoyalFuneralExileEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.addAbility(ability);

        // Legendary spells you cast cost {1} less to cast for each card exiled with The Kenriths' Royal Funeral.
        this.addAbility(new SimpleStaticAbility(new TheKenrithsRoyalFuneralCostEffect()));
    }

    private TheKenrithsRoyalFuneral(final TheKenrithsRoyalFuneral card) {
        super(card);
    }

    @Override
    public TheKenrithsRoyalFuneral copy() {
        return new TheKenrithsRoyalFuneral(this);
    }
}

class TheKenrithsRoyalFuneralExileEffect extends OneShotEffect {

    TheKenrithsRoyalFuneralExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to two target legendary creature cards from your graveyard. " +
                "You draw X cards and you lose X life, where X is the greatest mana value among cards exiled this way";
    }

    private TheKenrithsRoyalFuneralExileEffect(final TheKenrithsRoyalFuneralExileEffect effect) {
        super(effect);
    }

    @Override
    public TheKenrithsRoyalFuneralExileEffect copy() {
        return new TheKenrithsRoyalFuneralExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        cards.retainZone(Zone.GRAVEYARD, game);
        if (player == null || cards.isEmpty()) {
            return false;
        }
        int xValue = cards
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        player.drawCards(xValue, source, game);
        player.loseLife(xValue, game, source, false);
        return true;
    }
}

class TheKenrithsRoyalFuneralCostEffect extends CostModificationEffectImpl {

    TheKenrithsRoyalFuneralCostEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "legendary spells you cast cost {1} less to cast for each card exiled with {this}";
    }

    private TheKenrithsRoyalFuneralCostEffect(final TheKenrithsRoyalFuneralCostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())
        ));
        if (exileZone == null) {
            return false;
        }
        int amount = exileZone.size();
        CardUtil.reduceCost((SpellAbility) abilityToModify, amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility) || !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        if (!game.inCheckPlayableState()) {
            Spell spell = (Spell) game.getSpell(abilityToModify.getId());
            return spell != null && spell.isLegendary(game);
        }
        // Spell is not on the stack yet, but possible playable spells are determined
        Card sourceCard = game.getCard(abilityToModify.getSourceId());
        return sourceCard != null && sourceCard.isLegendary(game);
    }

    @Override
    public TheKenrithsRoyalFuneralCostEffect copy() {
        return new TheKenrithsRoyalFuneralCostEffect(this);
    }
}
