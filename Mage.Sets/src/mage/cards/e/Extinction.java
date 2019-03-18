
package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class Extinction extends CardImpl {

    public Extinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy all creatures of the creature type of your choice.
        this.getSpellAbility().addEffect(new ExtinctionEffect());
    }

    public Extinction(final Extinction card) {
        super(card);
    }

    @Override
    public Extinction copy() {
        return new Extinction(this);
    }
}

class ExtinctionEffect extends OneShotEffect {

    public ExtinctionEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Destroy all creatures of the creature type of your choice";
    }

    public ExtinctionEffect(final ExtinctionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (player != null && sourceObject != null) {
            Choice typeChoice = new ChoiceCreatureType(sourceObject);

            if (player.choose(outcome, typeChoice, game)) {
                game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
                FilterCreaturePermanent filterCreaturePermanent = new FilterCreaturePermanent();
                filterCreaturePermanent.add(new SubtypePredicate(SubType.byDescription(typeChoice.getChoice())));
                for (Permanent creature : game.getBattlefield().getActivePermanents(filterCreaturePermanent, source.getSourceId(), game)) {
                    creature.destroy(source.getSourceId(), game, true);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ExtinctionEffect copy() {
        return new ExtinctionEffect(this);
    }
}
