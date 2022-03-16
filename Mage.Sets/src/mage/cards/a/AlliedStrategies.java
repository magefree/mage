
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author FenrisulfrX
 */
public final class AlliedStrategies extends CardImpl {

    public AlliedStrategies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Domain - Target player draws a card for each basic land type among lands they control.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(DomainValue.TARGET));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);

    }

    private AlliedStrategies(final AlliedStrategies card) {
        super(card);
    }

    @Override
    public AlliedStrategies copy() {
        return new AlliedStrategies(this);
    }
}
