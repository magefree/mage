package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfMourning extends CardImpl {

    public WallOfMourning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Wall of Mourning enters the battlefield, exile a card from the top of your library face down for each opponent you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WallOfMourningExileEffect()));

        // Coven — At the beginning of your end step, if you control three or more creatures with different powers, put a card exiled with Wall of Mourning into its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new WallOfMourningReturnEffect(),
                TargetController.YOU, CovenCondition.instance, false
        ).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private WallOfMourning(final WallOfMourning card) {
        super(card);
    }

    @Override
    public WallOfMourning copy() {
        return new WallOfMourning(this);
    }
}

class WallOfMourningExileEffect extends OneShotEffect {

    WallOfMourningExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile a card from the top of your library face down for each opponent you have";
    }

    private WallOfMourningExileEffect(final WallOfMourningExileEffect effect) {
        super(effect);
    }

    @Override
    public WallOfMourningExileEffect copy() {
        return new WallOfMourningExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int opponents = OpponentsCount.instance.calculate(game, source, this);
        Set<Card> cards = player.getLibrary().getTopCards(game, opponents);
        cards.removeIf(Objects::isNull);
        player.moveCardsToExile(
                cards, source, game, false,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceLogName(game, source)
        );
        for (Card card : cards) {
            card.setFaceDown(true, game);
        }
        return true;
    }
}

class WallOfMourningReturnEffect extends OneShotEffect {

    WallOfMourningReturnEffect() {
        super(Outcome.Benefit);
        staticText = "put a card exiled with {this} into its owner's hand";
    }

    private WallOfMourningReturnEffect(final WallOfMourningReturnEffect effect) {
        super(effect);
    }

    @Override
    public WallOfMourningReturnEffect copy() {
        return new WallOfMourningReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        return player.moveCards(exileZone.getRandom(game), Zone.HAND, source, game);
    }
}
