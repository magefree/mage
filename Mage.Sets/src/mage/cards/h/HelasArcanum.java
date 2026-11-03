package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class HelasArcanum extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-Zombie creature with power 3 or less");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 3));
    }

    public HelasArcanum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        this.subtype.add(SubType.ARCANE);

        // TODO: Confirm if these are truly {P} or placeholder pawprints for set-specific stylised symbols
        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Target creature gets +1/+0 and gains deathtouch until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0).setText("target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
            .setText("and gains deathtouch until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Until end of turn, target creature gains "When this creature dies, return it to the battlefield tapped under its owner's control."
        Mode mode2 = new Mode(new GainAbilityTargetEffect(
            new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true, true)),
            Duration.EndOfTurn, "until end of turn, target creature gains \"When this creature dies, "
            + "return it to the battlefield tapped under its owner's control.\""
        ));
        mode2.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Destroy each non-Zombie creature with power 3 or less.
        Mode mode3 = new Mode(new DestroyAllEffect(filter));
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
    }

    private HelasArcanum(final HelasArcanum card) {
        super(card);
    }

    @Override
    public HelasArcanum copy() {
        return new HelasArcanum(this);
    }
}
