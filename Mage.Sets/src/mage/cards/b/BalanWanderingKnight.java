package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.AttachedToSourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Saga
 */
public final class BalanWanderingKnight extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.EQUIPMENT, "");

    static {
        filter.add(AttachedToSourcePredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, false);

    public BalanWanderingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT, SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Balan, Wandering Knight has double strike as long as two or more Equipment are attached to it.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance()), condition,
                "{this} has double strike as long as two or more Equipment are attached to it."
        )));

        // {1}{W}: Attach all Equipment you control to Balan.
        this.addAbility(new SimpleActivatedAbility(new BalanWanderingKnightEffect(), new ManaCostsImpl<>("{1}{W}")));
    }

    private BalanWanderingKnight(final BalanWanderingKnight card) {
        super(card);
    }

    @Override
    public BalanWanderingKnight copy() {
        return new BalanWanderingKnight(this);
    }

}

class BalanWanderingKnightEffect extends OneShotEffect {

    BalanWanderingKnightEffect() {
        super(Outcome.Benefit);
        this.staticText = "attach all Equipment you control to {this}.";
    }

    private BalanWanderingKnightEffect(final BalanWanderingKnightEffect effect) {
        super(effect);
    }

    @Override
    public BalanWanderingKnightEffect copy() {
        return new BalanWanderingKnightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        for (Permanent equipment : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT, source.getControllerId(), source, game
        )) {
            permanent.addAttachment(equipment.getId(), source, game);
        }
        return true;
    }
}
