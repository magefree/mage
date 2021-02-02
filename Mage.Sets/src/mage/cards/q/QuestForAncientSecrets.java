package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class QuestForAncientSecrets extends CardImpl {

    public QuestForAncientSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // Whenever a card is put into your graveyard from anywhere, you may put a quest counter on Quest for Ancient Secrets.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true, TargetController.YOU
        ));

        // Remove five quest counters from Quest for Ancient Secrets and sacrifice it: Target player shuffles their graveyard into their library.
        Ability ability = new SimpleActivatedAbility(
                new QuestForAncientSecretsEffect(),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)),
                        new SacrificeSourceCost(),
                        "Remove five quest counters from {this} and sacrifice it"
                )
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private QuestForAncientSecrets(final QuestForAncientSecrets card) {
        super(card);
    }

    @Override
    public QuestForAncientSecrets copy() {
        return new QuestForAncientSecrets(this);
    }
}

class QuestForAncientSecretsEffect extends OneShotEffect {

    QuestForAncientSecretsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their graveyard into their library";
    }

    private QuestForAncientSecretsEffect(final QuestForAncientSecretsEffect effect) {
        super(effect);
    }

    @Override
    public QuestForAncientSecretsEffect copy() {
        return new QuestForAncientSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.putCardsOnBottomOfLibrary(player.getGraveyard(), game, source, false);
        player.shuffleLibrary(source, game);
        return true;
    }
}
