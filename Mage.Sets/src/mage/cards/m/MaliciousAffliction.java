package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MaliciousAffliction extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public MaliciousAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        // <i>Morbid</i> &mdash; When you cast Malicious Affliction, if a creature died this turn, you may copy Malicious Affliction and may choose a new target for the copy.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new CopySourceSpellEffect(), true),
                MorbidCondition.instance, "<i>Morbid</i> &mdash; When you cast this spell, " +
                "if a creature died this turn, you may copy {this} and may choose a new target for the copy."
        );
        ability.setRuleAtTheTop(true);
        this.addAbility(ability.addHint(MorbidHint.instance));

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private MaliciousAffliction(final MaliciousAffliction card) {
        super(card);
    }

    @Override
    public MaliciousAffliction copy() {
        return new MaliciousAffliction(this);
    }
}
