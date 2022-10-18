package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AspiringChampion extends CardImpl {

    public AspiringChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Ruinous Ascension -- When Aspiring Champion deals combat damage to a player, sacrifice it. If you do, reveal cards from the top of your library until you reveal a creature card. Put that card onto the battlefield, then shuffle the rest into your library. If that creature is a Demon, it deals damage equal to its power to each opponent.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new AspiringChampionEffect(), false
        ).withFlavorWord("Ruinous Ascension"));
    }

    private AspiringChampion(final AspiringChampion card) {
        super(card);
    }

    @Override
    public AspiringChampion copy() {
        return new AspiringChampion(this);
    }
}

class AspiringChampionEffect extends OneShotEffect {

    AspiringChampionEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice it. If you do, reveal cards from the top of your library " +
                "until you reveal a creature card. Put that card onto the battlefield, " +
                "then shuffle the rest into your library. If that creature is a Demon, " +
                "it deals damage equal to its power to each opponent";
    }

    private AspiringChampionEffect(final AspiringChampionEffect effect) {
        super(effect);
    }

    @Override
    public AspiringChampionEffect copy() {
        return new AspiringChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Card card = getCard(player, toReveal, game);
        player.revealCards(source, toReveal, game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        toReveal.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        player.shuffleLibrary(source, game);
        Permanent creature = game.getPermanent(card.getId());
        if (creature == null
                || !creature.hasSubtype(SubType.DEMON, game)
                || creature.getPower().getValue() < 1) {
            return true;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            opponent.damage(creature.getPower().getValue(), creature.getId(), source, game);
        }
        return true;
    }

    private static Card getCard(Player player, Cards toReveal, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game)) {
                return card;
            }
        }
        return null;
    }
}
