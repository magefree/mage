package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.filter.FilterOpponent;
import mage.filter.FilterPermanent;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMasterGallifreysEnd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a nontoken artifact creature you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public TheMasterGallifreysEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Make Them Pay -- Whenever a nontoken artifact creature you control dies, you may exile it. If you do, choose an opponent with the most life among your opponents. That player faces a villainous choice -- They lose 4 life, or you create a token that's a copy of that card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new TheMasterGallifreysEndEffect(), true, filter, true
        ).withFlavorWord("Make Them Pay"));
    }

    private TheMasterGallifreysEnd(final TheMasterGallifreysEnd card) {
        super(card);
    }

    @Override
    public TheMasterGallifreysEnd copy() {
        return new TheMasterGallifreysEnd(this);
    }
}

enum TheMasterGallifreysEndPredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        return input
                .getObject()
                .getLife()
                >= game
                .getOpponents(input.getPlayerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .max()
                .orElse(Integer.MIN_VALUE);
    }
}

class TheMasterGallifreysEndEffect extends OneShotEffect {

    private static final FilterPlayer filter = new FilterOpponent("opponent with the most life among your opponents");

    static {
        filter.add(TheMasterGallifreysEndPredicate.instance);
    }

    TheMasterGallifreysEndEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If you do, choose an opponent with " +
                "the most life among your opponents. That player faces a villainous choice " +
                "&mdash; They lose 4 life, or you create a token that's a copy of that card";
    }

    private TheMasterGallifreysEndEffect(final TheMasterGallifreysEndEffect effect) {
        super(effect);
    }

    @Override
    public TheMasterGallifreysEndEffect copy() {
        return new TheMasterGallifreysEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        TargetPlayer target = new TargetPlayer(filter);
        target.withNotTarget(true);
        player.chooseTarget(outcome, target, source, game);
        Player opponent = game.getPlayer(target.getFirstTarget());
        return opponent != null && new FaceVillainousChoice(
                Outcome.LoseLife,
                new TheMasterGallifreysEndFirstChoice(),
                new TheMasterGallifreysEndSecondChoice(card)
        ).faceChoice(opponent, game, source);
    }
}

class TheMasterGallifreysEndFirstChoice extends VillainousChoice {

    TheMasterGallifreysEndFirstChoice() {
        super("", "Lose 4 life");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return player.loseLife(4, game, source, false) > 0;
    }
}

class TheMasterGallifreysEndSecondChoice extends VillainousChoice {

    private final Card card;

    TheMasterGallifreysEndSecondChoice(Card card) {
        super("", "{controller} creates a token that's a copy of the exiled card");
        this.card = card;
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new CreateTokenCopyTargetEffect()
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
    }
}
