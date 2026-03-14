package mage.cards.i;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ImpoliteEntrance extends CardImpl {

    public ImpoliteEntrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature gains trample and haste until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("target creature gains trample"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()).setText("and haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ImpoliteEntrance(final ImpoliteEntrance card) {
        super(card);
    }

    @Override
    public ImpoliteEntrance copy() {
        return new ImpoliteEntrance(this);
    }
}
