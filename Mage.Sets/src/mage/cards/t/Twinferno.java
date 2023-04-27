package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Twinferno extends CardImpl {

    public Twinferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one --
        // * When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()));

        // * Target creature you control gains double strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance())).addTarget(new TargetControlledCreaturePermanent()));
    }

    private Twinferno(final Twinferno card) {
        super(card);
    }

    @Override
    public Twinferno copy() {
        return new Twinferno(this);
    }
}
