package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavesOfChaosAdventurer extends CardImpl {

    public CavesOfChaosAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Caves of Chaos Adventurer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // Whenever Caves of Chaos Adventurer attacks, exile the top card of your library. If you've completed a dungeon, you may play that card this turn without paying its mana cost. Otherwise, you may play this card this turn.
        this.addAbility(new AttacksTriggeredAbility(new CavesOfChaosAdventurerEffect())
                .addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private CavesOfChaosAdventurer(final CavesOfChaosAdventurer card) {
        super(card);
    }

    @Override
    public CavesOfChaosAdventurer copy() {
        return new CavesOfChaosAdventurer(this);
    }
}

class CavesOfChaosAdventurerEffect extends OneShotEffect {

    CavesOfChaosAdventurerEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. If you've completed a dungeon, " +
                "you may play that card this turn without paying its mana cost. " +
                "Otherwise, you may play that card this turn";
    }

    private CavesOfChaosAdventurerEffect(final CavesOfChaosAdventurerEffect effect) {
        super(effect);
    }

    @Override
    public CavesOfChaosAdventurerEffect copy() {
        return new CavesOfChaosAdventurerEffect(this);
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
        player.moveCards(card, Zone.EXILED, source, game);
        if (CompletedDungeonWatcher.checkPlayer(source.getControllerId(), game)) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                    Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true
            ).setTargetPointer(new FixedTarget(card, game)), source);
        } else {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        return true;
    }
}
