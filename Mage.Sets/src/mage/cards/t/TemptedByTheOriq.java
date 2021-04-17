package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemptedByTheOriq extends CardImpl {

    public TemptedByTheOriq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}{U}");

        // For each opponent, gain control of up to one target creature or planeswalker that player controls with mana value 3 or less.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, gain control of up to one target creature " +
                        "or planeswalker that player controls with mana value 3 or less"));
        this.getSpellAbility().setTargetAdjuster(TemptedByTheOriqAdjuster.instance);
    }

    private TemptedByTheOriq(final TemptedByTheOriq card) {
        super(card);
    }

    @Override
    public TemptedByTheOriq copy() {
        return new TemptedByTheOriq(this);
    }
}

enum TemptedByTheOriqAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
                    "creature or planeswalker " + opponent.getName() + " controls with mana value 3 or less"
            );
            filter.add(new ControllerIdPredicate(opponentId));
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}
