package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class SiblingRivalry extends CardImpl {

    public SiblingRivalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Gain control of target artifact or creature until end of turn. Untap it. It gains haste until end of turn. Create a tapped Powerstone token.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect("untap it"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn, "it gains haste until end of turn"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new PowerstoneToken(), 1, true));
    }

    private SiblingRivalry(final SiblingRivalry card) {
        super(card);
    }

    @Override
    public SiblingRivalry copy() {
        return new SiblingRivalry(this);
    }
}
