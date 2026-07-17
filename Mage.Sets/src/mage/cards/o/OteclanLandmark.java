package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OteclanLandmark extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public OteclanLandmark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{W}",
                "Oteclan Levitator",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "W");

        // Oteclan Landmark
        this.getRightHalfCard().setPT(1, 4);

        // When Oteclan Landmark enters the battlefield, scry 2.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // Craft with artifact {2}{W}
        this.getLeftHalfCard().addAbility(new CraftAbility("{2}{W}"));

        // Oteclan Levitator
        this.getRightHalfCard().setPT(1, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever Oteclan Levitator attacks, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private OteclanLandmark(final OteclanLandmark card) {
        super(card);
    }

    @Override
    public OteclanLandmark copy() {
        return new OteclanLandmark(this);
    }
}
