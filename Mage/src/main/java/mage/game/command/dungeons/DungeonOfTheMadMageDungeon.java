package mage.game.command.dungeons;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.DungeonRoom;
import mage.game.permanent.token.SkeletonToken;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public class DungeonOfTheMadMageDungeon extends Dungeon {

    public DungeonOfTheMadMageDungeon() {
        super("Dungeon of the Mad Mage");
        // (1) Yawning Portal — You gain 1 life. (→ 2)
        DungeonRoom yawningPortal = new DungeonRoom("Yawning Portal", new GainLifeEffect(1));

        // (2) Dungeon Level — Scry 1. (→ 3a or 3b)
        DungeonRoom dungeonLevel = new DungeonRoom(
                "Dungeon Level", new ScryEffect(1, false)
        );

        // (3a) Goblin Bazaar — Create a Treasure token. (→ 4)
        DungeonRoom goblinBazaar = new DungeonRoom("Goblin Bazaar", new CreateTokenEffect(new TreasureToken()));

        // (3b) Twisted Caverns — Target creature can't attack until your next turn. (→ 4)
        DungeonRoom twistedCaverns = new DungeonRoom(
                "Twisted Caverns", new CantAttackTargetEffect(Duration.UntilYourNextTurn)
        );
        twistedCaverns.addTarget(new TargetCreaturePermanent());

        // (4) Lost Level — Scry 2. (→ 5a or 5b)
        DungeonRoom lostLevel = new DungeonRoom("Lost Level", new ScryEffect(2, false));

        // (5a) Runestone Caverns — Exile the top two cards of your library. You may play them. (→ 6)
        DungeonRoom runestoneCaverns = new DungeonRoom("Runestone Caverns", new RunestoneCavernsEffect());

        // (5b) Muiral's Graveyard — Create two 1/1 black Skeleton creature tokens. (→ 6)
        DungeonRoom muiralsGraveyard = new DungeonRoom(
                "Muiral's Graveyard", new CreateTokenEffect(new SkeletonToken(), 2)
        );

        // (6) Deep Mines — Scry 3. (→ 7)
        DungeonRoom deepMines = new DungeonRoom("Deep Mines", new ScryEffect(3, false));

        // (7) Mad Wizard's Lair — Draw three cards and reveal them. You may cast one of them without paying its mana cost.
        DungeonRoom madWizardsLair = new DungeonRoom("Mad Wizard's Lair", new MadWizardsLairEffect());

        yawningPortal.addNextRoom(dungeonLevel);
        dungeonLevel.addNextRoom(goblinBazaar);
        dungeonLevel.addNextRoom(twistedCaverns);
        goblinBazaar.addNextRoom(lostLevel);
        twistedCaverns.addNextRoom(lostLevel);
        lostLevel.addNextRoom(runestoneCaverns);
        lostLevel.addNextRoom(muiralsGraveyard);
        runestoneCaverns.addNextRoom(deepMines);
        muiralsGraveyard.addNextRoom(deepMines);
        deepMines.addNextRoom(madWizardsLair);

        this.addRoom(yawningPortal);
        this.addRoom(dungeonLevel);
        this.addRoom(goblinBazaar);
        this.addRoom(twistedCaverns);
        this.addRoom(lostLevel);
        this.addRoom(runestoneCaverns);
        this.addRoom(muiralsGraveyard);
        this.addRoom(deepMines);
        this.addRoom(madWizardsLair);
    }

    private DungeonOfTheMadMageDungeon(final DungeonOfTheMadMageDungeon dungeon) {
        super(dungeon);
    }

    public DungeonOfTheMadMageDungeon copy() {
        return new DungeonOfTheMadMageDungeon(this);
    }
}

class RunestoneCavernsEffect extends OneShotEffect {

    RunestoneCavernsEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. You may play them";
    }

    private RunestoneCavernsEffect(final RunestoneCavernsEffect effect) {
        super(effect);
    }

    @Override
    public RunestoneCavernsEffect copy() {
        return new RunestoneCavernsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        while (!cards.isEmpty()) {
            for (Card card : cards.getCards(game)) {
                if (!player.chooseUse(Outcome.PlayForFree, "Play " + card.getName() + "?", source, game)) {
                    continue;
                }
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                player.cast(
                        player.chooseAbilityForCast(card, game, false),
                        game, false, new ApprovingObject(source, game)
                );
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            }
            cards.retainZone(Zone.EXILED, game);
            if (cards.isEmpty() || !player.chooseUse(
                    Outcome.PlayForFree, "Continue playing the exiled cards?", source, game
            )) {
                break;
            }
        }
        return true;
    }
}

class MadWizardsLairEffect extends OneShotEffect {

    MadWizardsLairEffect() {
        super(Outcome.Benefit);
        staticText = "draw three cards and reveal them. You may cast one of them without paying its mana cost";
    }

    private MadWizardsLairEffect(final MadWizardsLairEffect effect) {
        super(effect);
    }

    @Override
    public MadWizardsLairEffect copy() {
        return new MadWizardsLairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 3));
        if (player.drawCards(3, source, game) != cards.size()) {
            return true;
        }
        player.revealCards(source, cards, game);
        TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_NON_LAND);
        player.choose(Outcome.PlayForFree, cards, target, source, game);
        Card card = player.getHand().get(target.getFirstTarget(), game);
        if (card == null) {
            return true;
        }
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(card, game, true),
                game, true, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return true;
    }
}
