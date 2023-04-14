package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HolgaRelentlessRager extends CardImpl {

    public HolgaRelentlessRager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Holga, Relentless Rager must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect()));

        // Whenever Holga attacks, each creature you control attacking a player gets +1/+0 until end of turn for each creature that player controls.
        this.addAbility(new AttacksTriggeredAbility(new HolgaRelentlessRagerEffect()));
    }

    private HolgaRelentlessRager(final HolgaRelentlessRager card) {
        super(card);
    }

    @Override
    public HolgaRelentlessRager copy() {
        return new HolgaRelentlessRager(this);
    }
}

class HolgaRelentlessRagerEffect extends OneShotEffect {
    private enum HolgaRelentlessRagerPredicate implements Predicate<Permanent> {
        instance;

        @Override
        public boolean apply(Permanent input, Game game) {
            return game.getPlayer(game.getCombat().getDefenderId(input.getId())) != null;
        }
    }

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(HolgaRelentlessRagerPredicate.instance);
    }

    HolgaRelentlessRagerEffect() {
        super(Outcome.Benefit);
        staticText = "each creature you control attacking a player gets +1/+0 " +
                "until end of turn for each creature that player controls";
    }

    private HolgaRelentlessRagerEffect(final HolgaRelentlessRagerEffect effect) {
        super(effect);
    }

    @Override
    public HolgaRelentlessRagerEffect copy() {
        return new HolgaRelentlessRagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            int count = game
                    .getBattlefield()
                    .count(StaticFilters.FILTER_CONTROLLED_CREATURE, game.getCombat().getDefenderId(permanent.getId()), source, game);
            if (count > 0) {
                game.addEffect(new BoostTargetEffect(
                        count, 0, Duration.EndOfTurn
                ).setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }
        return true;
    }
}
