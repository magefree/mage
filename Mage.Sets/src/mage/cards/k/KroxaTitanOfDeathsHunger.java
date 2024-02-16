package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KroxaTitanOfDeathsHunger extends CardImpl {

    public KroxaTitanOfDeathsHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Kroxa enters the battlefield, sacrifice it unless it escaped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KroxaTitanOfDeathsHungerEntersEffect()));

        // Whenever Kroxa enters the battlefield or attacks, each opponent discards a card, then each opponent who didn't discard a nonland card this way loses 3 life.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new KroxaTitanOfDeathsHungerDiscardEffect()));

        // Escape—{B}{B}{R}{R}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{B}{B}{R}{R}", 5));
    }

    private KroxaTitanOfDeathsHunger(final KroxaTitanOfDeathsHunger card) {
        super(card);
    }

    @Override
    public KroxaTitanOfDeathsHunger copy() {
        return new KroxaTitanOfDeathsHunger(this);
    }
}

class KroxaTitanOfDeathsHungerEntersEffect extends OneShotEffect {

    KroxaTitanOfDeathsHungerEntersEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice it unless it escaped";
    }

    private KroxaTitanOfDeathsHungerEntersEffect(final KroxaTitanOfDeathsHungerEntersEffect effect) {
        super(effect);
    }

    @Override
    public KroxaTitanOfDeathsHungerEntersEffect copy() {
        return new KroxaTitanOfDeathsHungerEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        if (EscapeAbility.wasCastedWithEscape(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())) {
            return false;
        }
        return permanent.sacrifice(source, game);
    }
}

class KroxaTitanOfDeathsHungerDiscardEffect extends OneShotEffect {

    KroxaTitanOfDeathsHungerDiscardEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent discards a card, " +
                "then each opponent who didn't discard a nonland card this way loses 3 life";
    }

    private KroxaTitanOfDeathsHungerDiscardEffect(final KroxaTitanOfDeathsHungerDiscardEffect effect) {
        super(effect);
    }

    @Override
    public KroxaTitanOfDeathsHungerDiscardEffect copy() {
        return new KroxaTitanOfDeathsHungerDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        game.getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> {
                    if (player.getHand().size() == 0) {
                        return;
                    }
                    TargetCard target = new TargetCardInHand(1, StaticFilters.FILTER_CARD);
                    player.choose(Outcome.Discard, player.getHand(), target, source, game);
                    cards.add(target.getFirstTarget());
                });
        Set<UUID> playerSet = new HashSet();
        cards.getCards(game)
                .stream()
                .forEachOrdered(card -> {
                    Player player = game.getPlayer(card.getOwnerId());
                    if (player == null
                            || !player.discard(card, false, source, game)
                            || card.isLand(game)) {
                        return;
                    }
                    playerSet.add(player.getId());
                });
        game.getOpponents(source.getControllerId())
                .stream()
                .filter(uuid -> !playerSet.contains(uuid))
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> player.loseLife(3, game, source, false));
        return true;
    }
}
