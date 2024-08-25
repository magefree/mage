package mage.cards.u;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UginsLabyrinth extends CardImpl {

    public UginsLabyrinth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Imprint -- When Ugin's Labyrinth enters the battlefield, you may exile a colorless card with mana value 7 or greater from your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UginsLabyrinthExileEffect())
                .setAbilityWord(AbilityWord.IMPRINT));

        // {T}: Add {C}. If a card is exiled with Ugin's Labyrinth, add {C}{C} instead.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new BasicManaEffect(Mana.ColorlessMana(2)),
                        new BasicManaEffect(Mana.ColorlessMana(1)),
                        UginsLabyrinthCondition.instance, "Add {C}. " +
                        "If a card is exiled with {this}, add {C}{C} instead."
                ), new TapSourceCost()
        ));

        // {T}: Return the exiled card to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new UginsLabyrinthReturnEffect(), new TapSourceCost()));
    }

    private UginsLabyrinth(final UginsLabyrinth card) {
        super(card);
    }

    @Override
    public UginsLabyrinth copy() {
        return new UginsLabyrinth(this);
    }
}

enum UginsLabyrinthCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getExile()::getExileZone)
                .map(x -> !x.isEmpty())
                .orElse(false);
    }
}

class UginsLabyrinthExileEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a colorless card with mana value 7 or greater");

    static {
        filter.add(ColorlessPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 6));
    }

    UginsLabyrinthExileEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile a colorless card with mana value 7 or greater from your hand";
    }

    private UginsLabyrinthExileEffect(final UginsLabyrinthExileEffect effect) {
        super(effect);
    }

    @Override
    public UginsLabyrinthExileEffect copy() {
        return new UginsLabyrinthExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
    }
}

class UginsLabyrinthReturnEffect extends OneShotEffect {

    UginsLabyrinthReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return the exiled card to its owner's hand";
    }

    private UginsLabyrinthReturnEffect(final UginsLabyrinthReturnEffect effect) {
        super(effect);
    }

    @Override
    public UginsLabyrinthReturnEffect copy() {
        return new UginsLabyrinthReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Optional.ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getExile()::getExileZone)
                .ifPresent(e -> player.moveCards(e, Zone.HAND, source, game));
        return true;
    }
}
