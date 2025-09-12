package mage.cards.m;

import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 * @author LevelX2
 */
public final class MaliciousAffliction extends CardImpl {

    public MaliciousAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");

        // <i>Morbid</i> &mdash; When you cast Malicious Affliction, if a creature died this turn, you may copy Malicious Affliction and may choose a new target for the copy.
        this.addAbility(new CastSourceTriggeredAbility(
                new CopySourceSpellEffect().setText("copy {this} and may choose a new target for the copy"), true
        ).withInterveningIf(MorbidCondition.instance).setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance).setRuleAtTheTop(true));

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private MaliciousAffliction(final MaliciousAffliction card) {
        super(card);
    }

    @Override
    public MaliciousAffliction copy() {
        return new MaliciousAffliction(this);
    }
}
