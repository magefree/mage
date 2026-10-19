package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.PrismaticAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.PerpetualUtil;
import mage.util.RandomUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class LaezelIllithidThrall extends CardImpl {

    public LaezelIllithidThrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.extraDeckCard = true;
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new LaezelSpecializedTriggeredAbility(new LaezelIllithidThrallEffect()));
    }

    private LaezelIllithidThrall(final LaezelIllithidThrall card) {
        super(card);
    }

    @Override
    public LaezelIllithidThrall copy() {
        return new LaezelIllithidThrall(this);
    }
}

class LaezelIllithidThrallEffect extends OneShotEffect {

    LaezelIllithidThrallEffect() {
        super(Outcome.Benefit);
        staticText = "conjure a duplicate of a random creature card from an opponent's library into your hand. It perpetually gains prismatic";
    }

    private LaezelIllithidThrallEffect(final LaezelIllithidThrallEffect effect) {
        super(effect);
    }

    @Override
    public LaezelIllithidThrallEffect copy() {
        return new LaezelIllithidThrallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        TargetOpponent opponentTarget = new TargetOpponent(true);
        if (player == null || !player.choose(outcome, opponentTarget, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(opponentTarget.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        List<Card> creatures = opponent.getLibrary().getCards(game).stream()
                .filter(card -> card.isCreature(game)).collect(Collectors.toList());
        Card original = RandomUtil.randomFromCollection(creatures);
        if (original == null) {
            return false;
        }
        Card duplicate = ConjureCardEffect.createCard(original.getName());
        if (duplicate == null) {
            return false;
        }
        game.loadCards(Collections.singleton(duplicate), player.getId());
        PerpetualUtil.addAbility(duplicate, new PrismaticAbility(), source, game);
        return player.moveCards(game.getCard(duplicate.getId()), Zone.HAND, source, game);
    }
}
