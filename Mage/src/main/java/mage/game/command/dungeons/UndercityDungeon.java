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
import mage.game.permanent.token.UndercitySkeletonToken;
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

        DungeonRoom secretEntrance = new DungeonRoom(
                "Secret Entrance",
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND)
                )
        );

        DungeonRoom forge = new DungeonRoom(
                "Forge", new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        forge.addTarget(new TargetCreaturePermanent());

        DungeonRoom lostWell = new DungeonRoom(
                "Lost Well", new ScryEffect(2, false)
        );

        DungeonRoom trap = new DungeonRoom("Trap!", new LoseLifeTargetEffect(5));
        trap.addTarget(new TargetPlayer());

        DungeonRoom arena = new DungeonRoom("Arena", new GoadTargetEffect());
        arena.addTarget(new TargetCreaturePermanent());

        DungeonRoom stash = new DungeonRoom("Stash", new CreateTokenEffect(new TreasureToken()));

        DungeonRoom archives = new DungeonRoom("Archives", new DrawCardSourceControllerEffect(1));

        DungeonRoom catacombs = new DungeonRoom("Catacombs", new CreateTokenEffect(new UndercitySkeletonToken()));

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
