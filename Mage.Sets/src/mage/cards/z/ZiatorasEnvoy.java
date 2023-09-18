package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZiatorasEnvoy extends CardImpl {

    public ZiatorasEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ziatora's Envoy deals combat damage to a player, look at the top card of your library. You may play a land from the top of your library or cast a spell with mana value less than or equal to the damage dealt from the top of your library without paying its mana cost. If you don't, put that card into your hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ZiatorasEnvoyEffect(), false));

        // Blitz {2}{B}{R}{G}
        this.addAbility(new BlitzAbility(this, "{2}{B}{R}{G}"));
    }

    private ZiatorasEnvoy(final ZiatorasEnvoy card) {
        super(card);
    }

    @Override
    public ZiatorasEnvoy copy() {
        return new ZiatorasEnvoy(this);
    }
}

class ZiatorasEnvoyEffect extends OneShotEffect {

    ZiatorasEnvoyEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. You may play a land from the top of your library " +
                "or cast a spell with mana value less than or equal to the damage dealt from the top of your " +
                "library without paying its mana cost. If you don't, put that card into your hand";
    }

    private ZiatorasEnvoyEffect(final ZiatorasEnvoyEffect effect) {
        super(effect);
    }

    @Override
    public ZiatorasEnvoyEffect copy() {
        return new ZiatorasEnvoyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top of your library", card, game);
        Cards cards = new CardsImpl(card);
        // TODO: factor this out and reuse for other cards
        if (player.canPlayLand()) {
            Set<Card> landSet = new HashSet<>();
            if (card instanceof ModalDoubleFacedCard) {
                ModalDoubleFacedCard mdfc = (ModalDoubleFacedCard) card;
                if (mdfc.getLeftHalfCard().isLand(game)) {
                    landSet.add(mdfc.getLeftHalfCard());
                }
                if (mdfc.getRightHalfCard().isLand(game)) {
                    landSet.add(mdfc.getRightHalfCard());
                }
            } else if (card.isLand(game)) {
                landSet.add(card);
            }
            Card land;
            if (!landSet.isEmpty() && player.chooseUse(
                    Outcome.PutLandInPlay, "Play " + card.getName() + " as a land?", source, game
            )) {
                switch (landSet.size()) {
                    case 1:
                        land = RandomUtil.randomFromCollection(landSet);
                        break;
                    case 2:
                        Iterator<Card> iterator = landSet.iterator();
                        Card land1 = iterator.next();
                        Card land2 = iterator.next();
                        land = player.chooseUse(
                                outcome, "Choose which land to play ", null,
                                land1.getName(), land2.getName(), source, game
                        ) ? land1 : land2;
                        break;
                    default:
                        land = null;
                }
            } else {
                land = null;
            }
            if (land != null) {
                player.playLand(land, game, true);
            }
        }
        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return true;
        }
        int damage = (Integer) this.getValue("damage");
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, damage + 1));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
        cards.retainZone(Zone.LIBRARY, game);
        return cards.isEmpty() || player.moveCards(cards, Zone.HAND, source, game);
    }
}
