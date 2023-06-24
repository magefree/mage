package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetCardCopyAndCastEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author alexander-novo
 */
public final class SarumanOfManyColors extends CardImpl {

    static final FilterCard filter = new FilterCard("enchantment, instant, or sorcery card");
    public static final Predicate<Card> predicate = Predicates.or(
            CardType.ENCHANTMENT.getPredicate(),
            CardType.INSTANT.getPredicate(),
            CardType.SORCERY.getPredicate());

    static {
        filter.add(predicate);
    }

    public SarumanOfManyColors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ward—Discard an enchantment, instant, or sorcery card.
        this.addAbility(new WardAbility(new DiscardTargetCost(new TargetCardInHand(filter)), false));

        // Whenever you cast your second spell each turn, each opponent mills two cards. When one or more cards are milled this way, exile target enchantment, instant, or sorcery card with equal or lesser mana value than that spell from an opponent's graveyard. Copy the exiled card. You may cast the copy without paying its mana cost.
        this.addAbility(new CastSecondSpellTriggeredAbility(Zone.BATTLEFIELD, new SarumanOfManyColorsEffect(),
                TargetController.YOU, false, SetTargetPointer.SPELL));
    }

    private SarumanOfManyColors(final SarumanOfManyColors card) {
        super(card);
    }

    @Override
    public SarumanOfManyColors copy() {
        return new SarumanOfManyColors(this);
    }
}

class SarumanOfManyColorsEffect extends OneShotEffect {

    public SarumanOfManyColorsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "each opponent mills two cards. When one or more cards are milled this way, exile target enchantment, instant, or sorcery card with equal or lesser mana value than that spell from an opponent's graveyard. Copy the exiled card. You may cast the copy without paying its mana cost.";
    }

    public SarumanOfManyColorsEffect(final SarumanOfManyColorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Keep track of whether or not the reflexive trigger needs to happen
        boolean trigger_second_part = false;

        // Each opponent mills two cards
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                // Only do the reflexive trigger if one or more cards were actually milled
                trigger_second_part |= !player.millCards(2, source, game).isEmpty();
            }
        }

        if (!trigger_second_part) {
            return true;
        }

        // The second spell cast is just referenced, not targetted, so we might need to get LKI if the spell doesn't exist anymore (such as if it were countered)
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));

        // Create a filter for "enchantment, instant, or sorcery card with equal or lesser mana value than that spell"
        FilterCard filter = new FilterCard(
                "enchantment, instant, or sorcery card with equal or lesser mana value than that spell from an opponent's graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, spell.getManaValue() + 1));
        filter.add(SarumanOfManyColors.predicate);

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new ExileTargetCardCopyAndCastEffect(true), false,
                "When one or more cards are milled this way, exile target enchantment, instant, or sorcery card with equal or lesser mana value than that spell from an opponent's graveyard."
                        + "Copy the exiled card. You may cast the copy without paying its mana cost.");
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));

        game.fireReflexiveTriggeredAbility(ability, source);

        return true;
    }

    @Override
    public SarumanOfManyColorsEffect copy() {
        return new SarumanOfManyColorsEffect(this);
    }

}