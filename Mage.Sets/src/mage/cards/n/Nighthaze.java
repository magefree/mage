

package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Nighthaze extends CardImpl {

    public Nighthaze (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new SwampwalkAbility(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Nighthaze (final Nighthaze card) {
        super(card);
    }

    @Override
    public Nighthaze copy() {
        return new Nighthaze(this);
    }

}
