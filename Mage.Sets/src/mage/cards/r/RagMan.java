package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class RagMan extends CardImpl {

    public RagMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {B}{B}{B}, {T}: Target opponent reveals their hand and discards a creature card at random. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new RevealHandTargetEffect(), new ManaCostsImpl<>("{B}{B}{B}"), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addEffect(new RagManDiscardEffect());
        ability.addTarget(new TargetOpponent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private RagMan(final RagMan card) {
        super(card);
    }

    @Override
    public RagMan copy() {
        return new RagMan(this);
    }
}

class RagManDiscardEffect extends OneShotEffect {

    public RagManDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "and discards a creature card at random";
    }

    public RagManDiscardEffect(final RagManDiscardEffect effect) {
        super(effect);
    }

    @Override
    public RagManDiscardEffect copy() {
        return new RagManDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            Cards creatureCardsInHand = new CardsImpl();
            for (UUID cardId : player.getHand()) {
                Card card = player.getHand().get(cardId, game);
                if (StaticFilters.FILTER_CARD_CREATURE.match(card, game)) {
                    creatureCardsInHand.add(card);
                }
            }

            if (!creatureCardsInHand.isEmpty()) {
                Card card = creatureCardsInHand.getRandom(game);
                player.discard(card, false, source, game);

            }

            return true;
        }
        return false;
    }

}
