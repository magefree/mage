
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author fireshoes
 */
public final class Outbreak extends CardImpl {

    private static final FilterCard filterLand = new FilterCard("a Swamp card");

    static {
        filterLand.add(SubType.SWAMP.getPredicate());
    }

    public Outbreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // You may discard a Swamp card rather than pay Outbreak's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filterLand))));

        // Choose a creature type. All creatures of that type get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new OutbreakEffect());
    }

    private Outbreak(final Outbreak card) {
        super(card);
    }

    @Override
    public Outbreak copy() {
        return new Outbreak(this);
    }
}

class OutbreakEffect extends OneShotEffect {

    public OutbreakEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Choose a creature type. All creatures of that type get -1/-1 until end of turn";
    }

    public OutbreakEffect(final OutbreakEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        if (player != null && player.choose(outcome, typeChoice, game)) {
            game.informPlayers(player.getLogName() + " has chosen " + typeChoice.getChoice());
            FilterCreaturePermanent filter = new FilterCreaturePermanent("All creatures of the chosen type");
            filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            ContinuousEffect effect = new BoostAllEffect(-1, -1, Duration.WhileOnBattlefield, filter, false);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public OutbreakEffect copy() {
        return new OutbreakEffect(this);
    }
}
