package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.GainLifeOpponentCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RoarOfJukai extends CardImpl {

    public RoarOfJukai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.subtype.add(SubType.ARCANE);

        // If you control a Forest, each blocked creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new RoarOfJukaiEffect());

        // Splice onto Arcane-An opponent gains 5 life.
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new GainLifeOpponentCost(5)));
    }

    private RoarOfJukai(final RoarOfJukai card) {
        super(card);
    }

    @Override
    public RoarOfJukai copy() {
        return new RoarOfJukai(this);
    }
}

class RoarOfJukaiEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Forest");
    private static final FilterCreaturePermanent filterBlocked = new FilterCreaturePermanent("blocked creature");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filterBlocked.add(BlockedPredicate.instance);
    }


    static {

    }

    public RoarOfJukaiEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If you control a Forest, each blocked creature gets +2/+2 until end of turn";
    }

    public RoarOfJukaiEffect(final RoarOfJukaiEffect effect) {
        super(effect);
    }

    @Override
    public RoarOfJukaiEffect copy() {
        return new RoarOfJukaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (new PermanentsOnTheBattlefieldCondition(filter).apply(game, source)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filterBlocked, source.getControllerId(), source, game)) {
                    ContinuousEffect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
