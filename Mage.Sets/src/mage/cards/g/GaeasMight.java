
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GaeasMight extends CardImpl {

    public GaeasMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Domain - Target creature gets +1/+1 until end of turn for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(DomainValue.REGULAR, DomainValue.REGULAR, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private GaeasMight(final GaeasMight card) {
        super(card);
    }

    @Override
    public GaeasMight copy() {
        return new GaeasMight(this);
    }
}
