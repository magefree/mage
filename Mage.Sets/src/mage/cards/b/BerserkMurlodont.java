package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BerserkMurlodont extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.BEAST, "a Beast");

    public BerserkMurlodont(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Beast becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new BerserkMurlodontEffect(), false, filter, false
        ));
    }

    private BerserkMurlodont(final BerserkMurlodont card) {
        super(card);
    }

    @Override
    public BerserkMurlodont copy() {
        return new BerserkMurlodont(this);
    }
}

class BerserkMurlodontEffect extends OneShotEffect {

    BerserkMurlodontEffect() {
        super(Outcome.Benefit);
        staticText = "it gets +1/+1 until end of turn for each creature blocking it";
    }

    private BerserkMurlodontEffect(final BerserkMurlodontEffect effect) {
        super(effect);
    }

    @Override
    public BerserkMurlodontEffect copy() {
        return new BerserkMurlodontEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int blockers = game
                .getCombat()
                .getGroups()
                .stream()
                .filter(combatGroup -> combatGroup.getAttackers().contains(permanent.getId()))
                .map(CombatGroup::getBlockers)
                .mapToInt(List::size)
                .sum();
        game.addEffect(new BoostTargetEffect(
                blockers, blockers, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
