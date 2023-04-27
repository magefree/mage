package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 *
 */
public final class PlanarDespair extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(DomainValue.REGULAR);

    public PlanarDespair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Domain - All creatures get -1/-1 until end of turn for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new BoostAllEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addHint(DomainHint.instance);
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private PlanarDespair(final PlanarDespair card) {
        super(card);
    }

    @Override
    public PlanarDespair copy() {
        return new PlanarDespair(this);
    }
}
