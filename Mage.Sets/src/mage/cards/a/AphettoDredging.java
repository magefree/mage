
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Quercitron
 */
public final class AphettoDredging extends CardImpl {

    public AphettoDredging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Return up to three target creature cards of the creature type of your choice from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return up to three target creature cards of the creature type of your choice from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            Player controller = game.getPlayer(ability.getControllerId());
            Choice typeChoice = new ChoiceCreatureType(game.getObject(ability.getSourceId()));
            if (controller != null && controller.choose(Outcome.PutCreatureInPlay, typeChoice, game)) {
                String chosenType = typeChoice.getChoice();
                FilterCreatureCard filter = new FilterCreatureCard(chosenType + " cards");
                filter.add(new SubtypePredicate(SubType.byDescription(chosenType)));
                ability.addTarget(new TargetCardInYourGraveyard(0, 3, filter));
            }
        }
    }

    public AphettoDredging(final AphettoDredging card) {
        super(card);
    }

    @Override
    public AphettoDredging copy() {
        return new AphettoDredging(this);
    }
}
