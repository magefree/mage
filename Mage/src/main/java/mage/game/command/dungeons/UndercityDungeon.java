package mage.game.command.dungeons;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Dungeon;
import mage.game.command.DungeonRoom;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.game.permanent.token.SkeletonMenaceToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 * @author TheElk801
 */
public class UndercityDungeon extends Dungeon {

    public UndercityDungeon() {
        super("Undercity", "CLB");

        // Secret Entrance — Search your library for a basic land card,
        //                   reveal it,
        //                   put it into your hand,
        //                   then shuffle. (Leads to: Forge, Lost Well)
        DungeonRoom secretEntrance = new DungeonRoom(
                "Secret Entrance",
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND)
                )
        );

        // Forge — Put two +1/+1 counters on target creature. (Leads to: Trap!, Arena)
        DungeonRoom forge = new DungeonRoom(
                "Forge", new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
        );
        forge.addTarget(new TargetCreaturePermanent());

        // Lost Well — Scry 2. (Leads to: Arena, Stash)
        DungeonRoom lostWell = new DungeonRoom(
                "Lost Well", new ScryEffect(2, false)
        );

        // Trap! — Target player loses 5 life. (Leads to: Archives)
        DungeonRoom trap = new DungeonRoom("Trap!", new LoseLifeTargetEffect(5));
        trap.addTarget(new TargetPlayer());

        // Arena — Goad target creature. (Leads to: Archives, Catacombs)
        DungeonRoom arena = new DungeonRoom("Arena", new GoadTargetEffect());
        arena.addTarget(new TargetCreaturePermanent());

        // Stash — Create a Treasure token. (Leads to: Catacombs)
        DungeonRoom stash = new DungeonRoom("Stash", new CreateTokenEffect(new TreasureToken()));

        // Archives — Draw a card. (Leads to: Throne of the Dead Three)
        DungeonRoom archives = new DungeonRoom("Archives", new DrawCardSourceControllerEffect(1));

        // Catacombs — Create a 4/1 black Skeleton creature token with menace. (Leads to: Throne of the Dead Three)
        DungeonRoom catacombs = new DungeonRoom("Catacombs", new CreateTokenEffect(new SkeletonMenaceToken()));

        // Throne of the Dead Three — Reveal the top ten cards of your library.
        //                            Put a creature card from among them onto the battlefield with three +1/+1 counters on it.
        //                            It gains hexproof until your next turn.
        //                            Then shuffle.
        DungeonRoom throneOfTheDeadThree = new DungeonRoom("Throne of the Dead Three", new ThroneOfTheDeadThreeEffect());

        secretEntrance.addNextRoom(forge);
        secretEntrance.addNextRoom(lostWell);
        forge.addNextRoom(trap);
        forge.addNextRoom(arena);
        lostWell.addNextRoom(arena);
        lostWell.addNextRoom(stash);
        trap.addNextRoom(archives);
        arena.addNextRoom(archives);
        arena.addNextRoom(catacombs);
        archives.addNextRoom(throneOfTheDeadThree);
        catacombs.addNextRoom(throneOfTheDeadThree);

        this.addRoom(secretEntrance);
        this.addRoom(forge);
        this.addRoom(lostWell);
        this.addRoom(trap);
        this.addRoom(arena);
        this.addRoom(stash);
        this.addRoom(archives);
        this.addRoom(catacombs);
        this.addRoom(throneOfTheDeadThree);
    }

    private UndercityDungeon(final UndercityDungeon dungeon) {
        super(dungeon);
    }

    public UndercityDungeon copy() {
        return new UndercityDungeon(this);
    }
}

class ThroneOfTheDeadThreeEffect extends OneShotEffect {

    ThroneOfTheDeadThreeEffect() {
        super(Outcome.Benefit);
        staticText = "Reveal the top ten cards of your library. Put a creature card from among them onto the " +
                "battlefield with three +1/+1 counters on it. It gains hexproof until your next turn. Then shuffle.";
    }

    private ThroneOfTheDeadThreeEffect(final ThroneOfTheDeadThreeEffect effect) {
        super(effect);
    }

    @Override
    public ThroneOfTheDeadThreeEffect copy() {
        return new ThroneOfTheDeadThreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 10));
        player.revealCards(source, cards, game);
        Card card;
        switch (cards.count(StaticFilters.FILTER_CARD_CREATURE, game)) {
            case 0:
                card = null;
                break;
            case 1:
                card = RandomUtil.randomFromCollection(cards.getCards(StaticFilters.FILTER_CARD_CREATURE, game));
                break;
            default:
                TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE);
                player.choose(outcome, cards, target, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(card.getId());
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            game.addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                    .setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
