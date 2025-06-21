package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastAnotherSpellThisTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.MonasteryMentorToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author balazskristof
 */
public final class RallyTheMonastery extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public RallyTheMonastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // This spell costs {2} less to cast if you've cast another spell this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionSourceEffect(2, CastAnotherSpellThisTurnCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(CastAnotherSpellThisTurnCondition.instance.getHint()));

        // Choose one —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // • Create two 1/1 white Monk creature tokens with prowess.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MonasteryMentorToken(), 2));

        // • Up to two target creatures you control each get +2/+2 until end of turn.
        this.getSpellAbility().getModes().addMode(new Mode(new BoostTargetEffect(2, 2))
                .addTarget(new TargetControlledCreaturePermanent(0, 2)));

        // • Destroy target creature with power 4 or greater.
        this.getSpellAbility().getModes().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter)));
    }

    private RallyTheMonastery(final RallyTheMonastery card) {
        super(card);
    }

    @Override
    public RallyTheMonastery copy() {
        return new RallyTheMonastery(this);
    }
}
