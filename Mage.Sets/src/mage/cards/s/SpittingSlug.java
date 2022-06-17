
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class SpittingSlug extends CardImpl {

    public SpittingSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Spitting Slug blocks or becomes blocked, you may pay {1}{G}. If you do, Spitting Slug gains first strike until end of turn. Otherwise, each creature blocking or blocked by Spitting Slug gains first strike until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(
            new DoIfCostPaid(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), 
                new SpittingSlugEffect(), 
                new ManaCostsImpl<>("{1}{G}")).setText("you may pay {1}{G}. If you do, {this} gains first strike until end of turn. Otherwise, each creature blocking or blocked by {this} gains first strike until end of turn"),
            false));
    }

    private SpittingSlug(final SpittingSlug card) {
        super(card);
    }

    @Override
    public SpittingSlug copy() {
        return new SpittingSlug(this);
    }
}

class SpittingSlugEffect extends OneShotEffect {

    public SpittingSlugEffect() {
        super(Outcome.Detriment);
    }

    public SpittingSlugEffect(final SpittingSlugEffect effect) {
        super(effect);
    }

    @Override
    public SpittingSlugEffect copy() {
        return new SpittingSlugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(new BlockedByIdPredicate(sourcePermanent.getId()), new BlockingAttackerIdPredicate(sourcePermanent.getId())));
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}
