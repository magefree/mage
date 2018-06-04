
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class QuestForAncientSecrets extends CardImpl {

    public QuestForAncientSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");


        // Whenever a card is put into your graveyard from anywhere, you may put a quest counter on Quest for Ancient Secrets.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true, TargetController.YOU));

        // Remove five quest counters from Quest for Ancient Secrets and sacrifice it: Target player shuffles their graveyard into their library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new QuestForAncientSecretsEffect(),
                new RemoveCountersSourceCost(CounterType.QUEST.createInstance(5)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public QuestForAncientSecrets(final QuestForAncientSecrets card) {
        super(card);
    }

    @Override
    public QuestForAncientSecrets copy() {
        return new QuestForAncientSecrets(this);
    }
}

class QuestForAncientSecretsEffect extends OneShotEffect {

    public QuestForAncientSecretsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their graveyard into their library";
    }

    public QuestForAncientSecretsEffect(final QuestForAncientSecretsEffect effect) {
        super(effect);
    }

    @Override
    public QuestForAncientSecretsEffect copy() {
        return new QuestForAncientSecretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }                
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
