package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KatsumasaTheAnimator extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");
    private static final FilterPermanent filter2
            = new FilterArtifactPermanent("noncreature artifacts");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter2.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public KatsumasaTheAnimator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{U}: Until end of turn, target noncreature artifact you control becomes an artifact creature and gains flying. If it's not a Vehicle, it has base power and toughness 1/1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new KatsumasaTheAnimatorEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // At the beginning of your upkeep, put a +1/+1 counter on each of up to three target noncreature artifacts.
        ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(0, 3, filter2));
        this.addAbility(ability);
    }

    private KatsumasaTheAnimator(final KatsumasaTheAnimator card) {
        super(card);
    }

    @Override
    public KatsumasaTheAnimator copy() {
        return new KatsumasaTheAnimator(this);
    }
}

class KatsumasaTheAnimatorEffect extends OneShotEffect {

    KatsumasaTheAnimatorEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, target noncreature artifact you control becomes an artifact creature " +
                "and gains flying. If it's not a Vehicle, it has base power and toughness 1/1 until end of turn";
    }

    private KatsumasaTheAnimatorEffect(final KatsumasaTheAnimatorEffect effect) {
        super(effect);
    }

    @Override
    public KatsumasaTheAnimatorEffect copy() {
        return new KatsumasaTheAnimatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new AddCardTypeTargetEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ), source);
        game.addEffect(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), source);
        if (!permanent.hasSubtype(SubType.VEHICLE, game)) {
            game.addEffect(new SetPowerToughnessTargetEffect(
                    1, 1, Duration.EndOfTurn
            ), source);
        }
        return true;
    }
}
