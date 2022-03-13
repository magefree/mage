package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author emerald000
 */
public final class CoordinatedBarrage extends CardImpl {

    public CoordinatedBarrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose a creature type. Coordinated Barrage deals damage to target attacking or blocking creature equal to the number of permanents you control of the chosen type.
        this.getSpellAbility().addEffect(new CoordinatedBarrageEffect());
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
    }

    private CoordinatedBarrage(final CoordinatedBarrage card) {
        super(card);
    }

    @Override
    public CoordinatedBarrage copy() {
        return new CoordinatedBarrage(this);
    }
}

class CoordinatedBarrageEffect extends OneShotEffect {

    CoordinatedBarrageEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose a creature type. {this} deals damage to target attacking or blocking creature equal to the number of permanents you control of the chosen type";
    }

    CoordinatedBarrageEffect(final CoordinatedBarrageEffect effect) {
        super(effect);
    }

    @Override
    public CoordinatedBarrageEffect copy() {
        return new CoordinatedBarrageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceCreatureType(game.getObject(source));
            if (controller.choose(Outcome.Damage, choice, game)) {
                String chosenType = choice.getChoice();
                FilterControlledPermanent filter = new FilterControlledPermanent();
                filter.add(SubType.byDescription(chosenType).getPredicate());
                int damageDealt = game.getBattlefield().count(filter, source.getControllerId(), source, game);
                Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    permanent.damage(damageDealt, source.getSourceId(), source, game, false, true);
                }
                return true;
            }
        }
        return false;
    }
}
