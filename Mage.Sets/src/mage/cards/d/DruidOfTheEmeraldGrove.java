package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DruidOfTheEmeraldGrove extends CardImpl {

    public DruidOfTheEmeraldGrove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Druid of the Emerald Grove enters the battlefield, search your library for up to two basic lands cards and reveal them, then roll a d20.
        // 1-9 | Put those cards into your hand, then shuffle.
        // 10-19 | Put one of those cards onto the battlefield tapped and the other into your hand, then shuffle.
        // 20 | Put those cards onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DruidOfTheEmeraldGroveEffect()));
    }

    private DruidOfTheEmeraldGrove(final DruidOfTheEmeraldGrove card) {
        super(card);
    }

    @Override
    public DruidOfTheEmeraldGrove copy() {
        return new DruidOfTheEmeraldGrove(this);
    }
}

class DruidOfTheEmeraldGroveEffect extends RollDieWithResultTableEffect {

    DruidOfTheEmeraldGroveEffect() {
        super(20, "search your library for up to two basic lands cards and reveal them, then roll a d20");
        this.addTableEntry(
                1, 9,
                new InfoEffect("put those cards into your hand, then shuffle")
        );
        this.addTableEntry(
                10, 19,
                new InfoEffect("put one of those cards onto the battlefield tapped and the other into your hand, then shuffle")
        );
        this.addTableEntry(
                20, 20,
                new InfoEffect("put those cards onto the battlefield tapped, then shuffle")
        );
    }

    private DruidOfTheEmeraldGroveEffect(final DruidOfTheEmeraldGroveEffect effect) {
        super(effect);
    }

    @Override
    public DruidOfTheEmeraldGroveEffect copy() {
        return new DruidOfTheEmeraldGroveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary targetCardInLibrary = new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS
        );
        player.searchLibrary(targetCardInLibrary, source, game);
        Cards cards = new CardsImpl();
        targetCardInLibrary
                .getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .filter(Objects::nonNull)
                .forEach(cards::add);
        player.revealCards(source, cards, game);
        int amount = player.rollDice(outcome, source, game, 20);
        if (amount < 1) {
        } else if (amount <= 9) {
            player.moveCards(cards, Zone.HAND, source, game);
        } else if (amount <= 19) {
            Card card;
            switch (cards.size()) {
                case 0:
                    card = null;
                    break;
                case 1:
                    card = cards.getRandom(game);
                    break;
                default:
                    TargetCard target = new TargetCardInLibrary();
                    player.choose(outcome, target, source, game);
                    card = cards.get(target.getFirstTarget(), game);
            }
            if (card != null) {
                player.moveCards(
                        card, Zone.BATTLEFIELD, source, game, true,
                        false, false, null
                );
                cards.remove(card);
            }
            player.moveCards(cards, Zone.HAND, source, game);
        } else if (amount != 20) {
            player.moveCards(
                    cards.getCards(game), Zone.BATTLEFIELD, source, game,
                    true, false, false, null
            );
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
