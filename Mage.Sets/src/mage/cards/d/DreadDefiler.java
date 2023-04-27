
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class DreadDefiler extends CardImpl {

    public DreadDefiler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {3}{C}, Exile a creature card from your graveyard: Target opponent loses life equal to the exiled card's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DreadDefilerEffect(), new ManaCostsImpl<>("{3}{C}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DreadDefiler(final DreadDefiler card) {
        super(card);
    }

    @Override
    public DreadDefiler copy() {
        return new DreadDefiler(this);
    }
}

class DreadDefilerEffect extends OneShotEffect {

    public DreadDefilerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Target opponent loses life equal to the exiled card's power";
    }

    public DreadDefilerEffect(final DreadDefilerEffect effect) {
        super(effect);
    }

    @Override
    public DreadDefilerEffect copy() {
        return new DreadDefilerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                Card card = game.getCard(cost.getTargets().getFirstTarget());
                if (card != null) {
                    amount = card.getPower().getValue();
                }
                break;
            }
        }
        if (amount > 0) {
            Player targetOpponent = game.getPlayer(source.getFirstTarget());
            if (targetOpponent != null) {
                targetOpponent.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }
}
