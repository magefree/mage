
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 * @author michael.napoleon@gmail.com
 */
public final class RoarOfTheCrowd extends CardImpl {

    public RoarOfTheCrowd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose a creature type. Roar of the Crowd deals damage to any target equal to the number of permanents you control of the chosen type.
        TargetAnyTarget target = new TargetAnyTarget();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new RoarOfTheCrowdEffect());
    }

    private RoarOfTheCrowd(final RoarOfTheCrowd card) {
        super(card);
    }

    @Override
    public RoarOfTheCrowd copy() {
        return new RoarOfTheCrowd(this);
    }
}

class RoarOfTheCrowdEffect extends OneShotEffect {

    RoarOfTheCrowdEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Choose a creature type. {this} deals damage to any target equal to the number of permanents you control of the chosen type.";
    }

    RoarOfTheCrowdEffect(final RoarOfTheCrowdEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfTheCrowdEffect copy() {
        return new RoarOfTheCrowdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
            if (!player.choose(Outcome.LoseLife, typeChoice, game)) {
                return false;
            }
            FilterControlledPermanent filter = new FilterControlledPermanent();
            filter.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());
            return new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)).apply(game, source);
        }
        return false;
    }
}
