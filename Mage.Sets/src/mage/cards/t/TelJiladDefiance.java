package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterArtifactCard;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author ayratn
 */
public final class TelJiladDefiance extends CardImpl {

    public TelJiladDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ProtectionAbility.from(CardType.ARTIFACT)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private TelJiladDefiance(final TelJiladDefiance card) {
        super(card);
    }

    @Override
    public TelJiladDefiance copy() {
        return new TelJiladDefiance(this);
    }
}
