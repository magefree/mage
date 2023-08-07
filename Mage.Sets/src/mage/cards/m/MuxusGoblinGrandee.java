package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuxusGoblinGrandee extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public MuxusGoblinGrandee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Muxus, Goblin Grandee enters the battlefield, reveal the top six cards of your library. Put all Goblin creature cards with converted mana cost 5 or less from among them onto the battlefield and the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MuxusGoblinGrandeeEffect()));

        // Whenever Muxus attacks, it gets +1/+1 until end of turn for each other Goblin you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("it gets +1/+1 until end of turn for each other Goblin you control"), false));
    }

    private MuxusGoblinGrandee(final MuxusGoblinGrandee card) {
        super(card);
    }

    @Override
    public MuxusGoblinGrandee copy() {
        return new MuxusGoblinGrandee(this);
    }
}

class MuxusGoblinGrandeeEffect extends OneShotEffect {

    MuxusGoblinGrandeeEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top six cards of your library. " +
                "Put all Goblin creature cards with mana value 5 or less " +
                "from among them onto the battlefield and the rest on the bottom of your library in a random order.";
    }

    private MuxusGoblinGrandeeEffect(final MuxusGoblinGrandeeEffect effect) {
        super(effect);
    }

    @Override
    public MuxusGoblinGrandeeEffect copy() {
        return new MuxusGoblinGrandeeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);
        Cards toBattlfield = new CardsImpl();
        Cards toBottom = new CardsImpl();
        cards.getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .forEach(card -> {
                    if (card.isCreature(game)
                            && card.hasSubtype(SubType.GOBLIN, game)
                            && card.getManaValue() <= 5) {
                        toBattlfield.add(card);
                    } else {
                        toBottom.add(card);
                    }
                });
        player.moveCards(toBattlfield, Zone.BATTLEFIELD, source, game);
        // need to account for effects like Grafdigger's Cage
        toBattlfield
                .stream()
                .filter(uuid -> game.getState().getZone(uuid) == Zone.LIBRARY)
                .forEach(toBottom::add);
        player.putCardsOnBottomOfLibrary(toBottom, game, source, false);
        return true;
    }
}
