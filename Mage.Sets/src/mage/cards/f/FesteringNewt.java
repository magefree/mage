
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FesteringNewt extends CardImpl {

    private static final FilterCreaturePermanent filterBogbrewWitch = new FilterCreaturePermanent();

    static {
        filterBogbrewWitch.add(new NamePredicate("Bogbrew Witch"));
    }

    public FesteringNewt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.SALAMANDER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Festering Newt dies, target creature an opponent controls gets -1/-1 until end of turn. That creature gets -4/-4 instead if you control a creature named Bogbrew Witch.
        Effect effect = new ConditionalContinuousEffect(
                new BoostTargetEffect(-4,-4, Duration.EndOfTurn), 
                new BoostTargetEffect(-1,-1, Duration.EndOfTurn),
                new LockedInCondition(new PermanentsOnTheBattlefieldCondition(filterBogbrewWitch)),
                "target creature an opponent controls gets -1/-1 until end of turn. That creature gets -4/-4 instead if you control a creature named Bogbrew Witch");
        Ability ability = new DiesSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private FesteringNewt(final FesteringNewt card) {
        super(card);
    }

    @Override
    public FesteringNewt copy() {
        return new FesteringNewt(this);
    }
}
