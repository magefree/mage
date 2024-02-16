
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class AshenGhoul extends CardImpl {

    public AshenGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {B}: Return Ashen Ghoul from your graveyard to the battlefield. Activate this ability only during your upkeep and only if three or more creature cards are above Ashen Ghoul.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new ManaCostsImpl<>("{B}"),
                AshenGhoulCondition.instance
        ));
    }

    private AshenGhoul(final AshenGhoul card) {
        super(card);
    }

    @Override
    public AshenGhoul copy() {
        return new AshenGhoul(this);
    }
}

enum AshenGhoulCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (game.getTurnStepType() != PhaseStep.UPKEEP
                || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        if (controller != null) {
            int cardsAbove = 0;
            boolean aboveCards = false;
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (aboveCards && card.isCreature(game)) {
                    cardsAbove++;
                    if (cardsAbove > 2) {
                        return true;
                    }
                }
                if (card.getId().equals(source.getSourceId())) {
                    aboveCards = true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "only during your upkeep and only if three or more creature cards are above {this}";
    }
}
