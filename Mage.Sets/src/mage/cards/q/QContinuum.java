package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class QContinuum extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
        new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public QContinuum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control two or more other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // When this land enters untapped, choose one --
        // * Target creature gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new EntersBattlefieldUntappedTriggeredAbility(
            new BoostTargetEffect(1, 1).setText("target creature gets +1/+1"), false
        );
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
            .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());

        // * Surveil 1.
        ability.addMode(new Mode(new SurveilEffect(1)));
        this.addAbility(ability);

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private QContinuum(final QContinuum card) {
        super(card);
    }

    @Override
    public QContinuum copy() {
        return new QContinuum(this);
    }
}
