package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class NathanDrakeTreasureHunter extends CardImpl {

    public NathanDrakeTreasureHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // You may spend mana as though it were mana of any color to cast spells you don't own or to activate abilities of permanents you control but don't own.
        this.addAbility(new SimpleStaticAbility(new NathanDrakeTreasureHunterManaEffect()));

        // Whenever Nathan Drake attacks, exile the top card of each player's library. You may cast a spell from among those cards.
        this.addAbility(new AttacksTriggeredAbility(new NathanDrakeTreasureHunterCastEffect()));
    }

    private NathanDrakeTreasureHunter(final NathanDrakeTreasureHunter card) {
        super(card);
    }

    @Override
    public NathanDrakeTreasureHunter copy() {
        return new NathanDrakeTreasureHunter(this);
    }
}

class NathanDrakeTreasureHunterManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    NathanDrakeTreasureHunterManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast spells you don't own " +
                "or to activate abilities of permanents you control but don't own";
    }

    private NathanDrakeTreasureHunterManaEffect(final NathanDrakeTreasureHunterManaEffect effect) {
        super(effect);
    }

    @Override
    public NathanDrakeTreasureHunterManaEffect copy() {
        return new NathanDrakeTreasureHunterManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(CardUtil.getMainCardId(game, objectId));
        return card != null && !card.isOwnedBy(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

class NathanDrakeTreasureHunterCastEffect extends OneShotEffect {

    NathanDrakeTreasureHunterCastEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each player's library. You may cast a spell from among those cards";
    }

    private NathanDrakeTreasureHunterCastEffect(final NathanDrakeTreasureHunterCastEffect effect) {
        super(effect);
    }

    @Override
    public NathanDrakeTreasureHunterCastEffect copy() {
        return new NathanDrakeTreasureHunterCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .collect(Collectors.toSet()));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> CardUtil.getCastableComponents(
                game.getCard(uuid), StaticFilters.FILTER_CARD,
                source, player, game, null, false
        ).isEmpty());
        TargetCard target = new TargetCardInExile(0, 1, StaticFilters.FILTER_CARD);
        target.withChooseHint("to cast");
        target.withNotTarget(true);
        player.choose(Outcome.DrawCard, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        CardUtil.castSingle(player, source, game, card);
        return true;
    }
}
