
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author TheElk801
 */
public final class KrovikanHorror extends CardImpl {

    public KrovikanHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of the end step, if Krovikan Horror is in your graveyard with a creature card directly above it, you may return Krovikan Horror to your hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                TargetController.ANY, KrovikanHorrorCondition.instance, true
        ));

        // {1}, Sacrifice a creature: Krovikan Horror deals 1 damage to any target.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private KrovikanHorror(final KrovikanHorror card) {
        super(card);
    }

    @Override
    public KrovikanHorror copy() {
        return new KrovikanHorror(this);
    }
}

enum KrovikanHorrorCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean nextCard = false;
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (nextCard) {
                    return card.isCreature(game);
                }
                if (card.getId().equals(source.getSourceId())) {
                    nextCard = true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is in your graveyard with a creature card directly above it";
    }
}
