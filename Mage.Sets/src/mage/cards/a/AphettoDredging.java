
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class AphettoDredging extends CardImpl {

    public AphettoDredging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Return up to three target creature cards of the creature type of your choice from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return up to three target creature cards of the creature type of your choice from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(AphettoDredgingAdjuster.instance);
    }

    private AphettoDredging(final AphettoDredging card) {
        super(card);
    }

    @Override
    public AphettoDredging copy() {
        return new AphettoDredging(this);
    }
}

enum AphettoDredgingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(ability.getSourceId()));
        if (controller != null && controller.choose(Outcome.PutCreatureInPlay, typeChoice, game)) {
            String chosenType = typeChoice.getChoice();
            FilterCreatureCard filter = new FilterCreatureCard(chosenType + " cards");
            filter.add(SubType.byDescription(chosenType).getPredicate());
            ability.addTarget(new TargetCardInYourGraveyard(0, 3, filter));
        }
    }
}