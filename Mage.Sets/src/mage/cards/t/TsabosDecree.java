package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TsabosDecree extends CardImpl {

    public TsabosDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{B}");

        // Choose a creature type. Target player reveals their hand and discards all creature cards of that type. Then destroy all creatures of that type that player controls. They can't be regenerated.
        this.getSpellAbility().addEffect(new TsabosDecreeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TsabosDecree(final TsabosDecree card) {
        super(card);
    }

    @Override
    public TsabosDecree copy() {
        return new TsabosDecree(this);
    }
}

class TsabosDecreeEffect extends OneShotEffect {

    TsabosDecreeEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Choose a creature type. Target player reveals their hand and discards " +
                "all creature cards of that type. Then destroy all creatures of that type that player controls. " +
                "They can't be regenerated";
    }

    private TsabosDecreeEffect(final TsabosDecreeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (player == null) {
            return false;
        }
        if (sourceObject == null) {
            return false;
        }
        Choice typeChoice = new ChoiceCreatureType(sourceObject);
        if (!player.choose(outcome, typeChoice, game)) {
            return false;
        }
        game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
        targetPlayer.revealCards("hand of " + targetPlayer.getName(), targetPlayer.getHand(), game);
        FilterCard filterCard = new FilterCard();
        filterCard.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
        targetPlayer.discard(new CardsImpl(targetPlayer.getHand().getCards(filterCard, game)), false, source, game);
        FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
        filterCreaturePermanent.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
        for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
            if (creature.isControlledBy(targetPlayer.getId())) {
                creature.destroy(source, game, true);
            }
        }
        return true;
    }

    @Override
    public TsabosDecreeEffect copy() {
        return new TsabosDecreeEffect(this);
    }
}
