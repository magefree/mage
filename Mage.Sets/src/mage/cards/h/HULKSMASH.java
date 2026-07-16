package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class HULKSMASH extends CardImpl {

    public HULKSMASH(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Teamwork 4
        this.addAbility(new TeamworkAbility(4));

        // Choose one. If this spell was cast using teamwork, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
            "Choose one. If this spell was cast using teamwork, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, TeamworkCondition.instance);

        // * Destroy target noncreature artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ARTIFACT_NON_CREATURE));

        // * Target creature you control deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addMode(new Mode(new DamageWithPowerFromOneToAnotherTargetEffect())
            .addTarget(new TargetControlledCreaturePermanent())
            .addTarget(new TargetOpponentsCreaturePermanent()));
    }

    private HULKSMASH(final HULKSMASH card) {
        super(card);
    }

    @Override
    public HULKSMASH copy() {
        return new HULKSMASH(this);
    }
}
