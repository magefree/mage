
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class DistantMelody extends CardImpl {

    public DistantMelody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose a creature type. Draw a card for each permanent you control of that type.
        this.getSpellAbility().addEffect(new DistantMelodyEffect());
    }

    private DistantMelody(final DistantMelody card) {
        super(card);
    }

    @Override
    public DistantMelody copy() {
        return new DistantMelody(this);
    }
}

class DistantMelodyEffect extends OneShotEffect {

    DistantMelodyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose a creature type. Draw a card for each permanent you control of that type";
    }

    DistantMelodyEffect(final DistantMelodyEffect effect) {
        super(effect);
    }

    @Override
    public DistantMelodyEffect copy() {
        return new DistantMelodyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        if (controller != null && controller.choose(Outcome.BoostCreature, typeChoice, game)) {
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
            filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            return new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)).apply(game, source);
        }
        return false;
    }
}
