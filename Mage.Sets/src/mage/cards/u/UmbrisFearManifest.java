package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmbrisFearManifest extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Nightmare or Horror");

    static {
        filter.add(Predicates.or(
                SubType.NIGHTMARE.getPredicate(),
                SubType.HORROR.getPredicate()
        ));
    }

    public UmbrisFearManifest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Umbris, Fear Manifest gets +1/+1 for each card your opponents own in exile.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                UmbrisFearManifestValue.instance,
                UmbrisFearManifestValue.instance,
                Duration.WhileOnBattlefield
        )));

        // Whenever Umbris or another Nightmare or Horror enters the battlefield under your control, target opponent exiles cards from the top of their library until they exile a land card.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new UmbrisFearManifestEffect(), filter, false, true
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private UmbrisFearManifest(final UmbrisFearManifest card) {
        super(card);
    }

    @Override
    public UmbrisFearManifest copy() {
        return new UmbrisFearManifest(this);
    }
}

enum UmbrisFearManifestValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getExile()
                .getExileZones()
                .stream()
                .map(exileZone -> exileZone.getCards(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(Card::getOwnerId)
                .filter(game.getOpponents(sourceAbility.getControllerId())::contains)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public UmbrisFearManifestValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "card your opponents own in exile";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class UmbrisFearManifestEffect extends OneShotEffect {

    UmbrisFearManifestEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent exiles cards from the top of their library until they exile a land card";
    }

    private UmbrisFearManifestEffect(final UmbrisFearManifestEffect effect) {
        super(effect);
    }

    @Override
    public UmbrisFearManifestEffect copy() {
        return new UmbrisFearManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            if (card.isLand(game)) {
                break;
            }
        }
        return true;
    }
}
