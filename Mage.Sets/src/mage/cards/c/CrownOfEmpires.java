package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class CrownOfEmpires extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();
    private static final FilterPermanent filter2 = new FilterControlledArtifactPermanent();

    static {
        filter.add(new NamePredicate("Scepter of Empires"));
        filter2.add(new NamePredicate("Throne of Empires"));
    }

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(filter),
            new PermanentsOnTheBattlefieldCondition(filter2)
    );

    public CrownOfEmpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {tap}: Tap target creature. Gain control of that creature instead if you control artifacts named Scepter of Empires and Throne of Empires.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new GainControlTargetEffect(Duration.Custom)),
                new TapTargetEffect(), condition, "tap target creature. Gain control of that creature " +
                "instead if you control artifacts named Scepter of Empires and Throne of Empires"
        ), new GenericManaCost(3));
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CrownOfEmpires(final CrownOfEmpires card) {
        super(card);
    }

    @Override
    public CrownOfEmpires copy() {
        return new CrownOfEmpires(this);
    }
}
