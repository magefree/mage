
package mage.cards.e;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author FenrisulfrX
 */
public final class EvasiveAction extends CardImpl {

    public EvasiveAction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Domain - Counter target spell unless its controller pays {1} for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(DomainValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);
    }

    private EvasiveAction(final EvasiveAction card) {
        super(card);
    }

    @Override
    public EvasiveAction copy() {
        return new EvasiveAction(this);
    }
}
