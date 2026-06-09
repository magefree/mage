package mage.cards.w;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class WidowsBite extends CardImpl {

    public WidowsBite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");


        // Teamwork 3
        this.addAbility(new TeamworkAbility(3));

        // Choose one. If this spell was cast using teamwork, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
            "Choose one. If this spell was cast using teamwork, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, TeamworkCondition.instance);

        // * Target creature gains deathtouch until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Target creature gets -2/-2 until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(-2, -2));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private WidowsBite(final WidowsBite card) {
        super(card);
    }

    @Override
    public WidowsBite copy() {
        return new WidowsBite(this);
    }
}
