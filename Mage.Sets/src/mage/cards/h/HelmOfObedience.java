package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Plopman
 */
public final class HelmOfObedience extends CardImpl {

    public HelmOfObedience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {X}, {T}: Target opponent puts cards from the top of their library into their graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        VariableManaCost xCosts = new VariableManaCost();
        xCosts.setMinX(1);
        SimpleActivatedAbility abilitiy = new SimpleActivatedAbility(new HelmOfObedienceEffect(), xCosts);
        abilitiy.addCost(new TapSourceCost());
        abilitiy.addTarget(new TargetOpponent());
        this.addAbility(abilitiy);
    }

    private HelmOfObedience(final HelmOfObedience card) {
        super(card);
    }

    @Override
    public HelmOfObedience copy() {
        return new HelmOfObedience(this);
    }
}

class HelmOfObedienceEffect extends OneShotEffect {

    private static final ManacostVariableValue amount = ManacostVariableValue.instance;

    HelmOfObedienceEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent mills a card, then repeats this process until a creature card " +
                "or X cards have been put into their graveyard this way, whichever comes first. " +
                "If one or more creature cards were put into that graveyard this way, " +
                "sacrifice {this} and put one of them onto the battlefield under your control. X can't be 0";
    }

    private HelmOfObedienceEffect(final HelmOfObedienceEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfObedienceEffect copy() {
        return new HelmOfObedienceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        int max = amount.calculate(game, source, this);
        if (targetOpponent == null || controller == null || max <= 0) {
            return false;
        }
        int numberOfCard = 0;
        while (targetOpponent.getLibrary().hasCards()) {
            Cards cards = targetOpponent.millCards(1, source, game);
            cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.GRAVEYARD);
            numberOfCard += cards.size();
            Set<Card> creatures = cards
                    .getCards(game)
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(MageObject::isCreature)
                    .collect(Collectors.toSet());
            if (!creatures.isEmpty()) {
                controller.moveCards(creatures, Zone.BATTLEFIELD, source, game);
            }
            if (!creatures.isEmpty() || numberOfCard >= max) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
                break;
            }
        }
        return true;
    }

}
