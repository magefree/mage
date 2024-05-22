package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmoQueenOfVesuva extends CardImpl {

    public OmoQueenOfVesuva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Whenever Omo, Queen of Vesuva enters the battlefield or attacks, put an everything counter on each of up to one target land and up to one target creature.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersTargetEffect(CounterType.EVERYTHING.createInstance())
                        .setTargetPointer(new EachTargetPointer())
                        .setText("put an everything counter on each of up to one target land and up to one target creature")
        );
        ability.addTarget(new TargetLandPermanent(0, 1));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Each land with an everything counter on it is every land type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new OmoQueenOfVesuvaLandEffect()));

        // Each nonland creature with an everything counter on it is every creature type.
        this.addAbility(new SimpleStaticAbility(new OmoQueenOfVesuvaCreatureEffect()));
    }

    private OmoQueenOfVesuva(final OmoQueenOfVesuva card) {
        super(card);
    }

    @Override
    public OmoQueenOfVesuva copy() {
        return new OmoQueenOfVesuva(this);
    }
}

class OmoQueenOfVesuvaLandEffect extends ContinuousEffectImpl {

    private static final Ability[] basicManaAbilities = {
            new WhiteManaAbility(),
            new BlueManaAbility(),
            new BlackManaAbility(),
            new RedManaAbility(),
            new GreenManaAbility()
    };
    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(CounterType.EVERYTHING.getPredicate());
    }

    public OmoQueenOfVesuvaLandEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "each land with an everything counter on it is every land type in addition to its other types";
        dependencyTypes.add(DependencyType.BecomeMountain);
        dependencyTypes.add(DependencyType.BecomeForest);
        dependencyTypes.add(DependencyType.BecomeSwamp);
        dependencyTypes.add(DependencyType.BecomeIsland);
        dependencyTypes.add(DependencyType.BecomePlains);
    }

    private OmoQueenOfVesuvaLandEffect(final OmoQueenOfVesuvaLandEffect effect) {
        super(effect);
    }

    @Override
    public OmoQueenOfVesuvaLandEffect copy() {
        return new OmoQueenOfVesuvaLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.addSubType(
                    game,
                    SubType.PLAINS,
                    SubType.ISLAND,
                    SubType.SWAMP,
                    SubType.MOUNTAIN,
                    SubType.FOREST
            );
            permanent.setIsAllNonbasicLandTypes(game, true);
            // Optimization: Remove basic mana abilities since they are redundant with AnyColorManaAbility
            //               and keeping them will only produce too many combinations inside ManaOptions
            for (Ability basicManaAbility : basicManaAbilities) {
                if (permanent.getAbilities(game).containsRule(basicManaAbility)) {
                    permanent.removeAbility(basicManaAbility, source.getSourceId(), game);
                }
            }
            // Add the {T}: Add one mana of any color ability
            // This is functionally equivalent to having five "{T}: Add {COLOR}" for each COLOR in {W}{U}{B}{R}{G}
            AnyColorManaAbility ability = new AnyColorManaAbility();
            if (!permanent.getAbilities(game).containsRule(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}

class OmoQueenOfVesuvaCreatureEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent();

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(CounterType.EVERYTHING.getPredicate());
    }

    public OmoQueenOfVesuvaCreatureEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "each nonland creature with an everything counter on it is every creature type";
        dependencyTypes.add(DependencyType.AddingCreatureType);
    }

    private OmoQueenOfVesuvaCreatureEffect(final OmoQueenOfVesuvaCreatureEffect effect) {
        super(effect);
    }

    @Override
    public OmoQueenOfVesuvaCreatureEffect copy() {
        return new OmoQueenOfVesuvaCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.setIsAllCreatureTypes(game, true);
        }
        return true;
    }
}
