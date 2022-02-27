package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Cromat extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature blocking or blocked by {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public Cromat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {W}{B}: Destroy target creature blocking or blocked by Cromat.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect(), new ManaCostsImpl<>("{W}{B}")
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {U}{R}: Cromat gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{U}{R}")));

        // {B}{G}: Regenerate Cromat.
        this.addAbility(new SimpleActivatedAbility(
                new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}{G}")
        ));

        // {R}{W}: Cromat gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 1, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}{W}")));

        // {G}{U}: Put Cromat on top of its owner's library.
        this.addAbility(new SimpleActivatedAbility(
                new PutOnLibrarySourceEffect(true), new ManaCostsImpl<>("{G}{U}")
        ));
    }

    private Cromat(final Cromat card) {
        super(card);
    }

    @Override
    public Cromat copy() {
        return new Cromat(this);
    }
}
