
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author LoneFox
 */
public final class Stratadon extends CardImpl {

    public Stratadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{10}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Domain -- This spell costs {1} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(DomainValue.REGULAR)
                .setText("This spell costs {1} less to cast for each basic land type among lands you control")
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private Stratadon(final Stratadon card) {
        super(card);
    }

    @Override
    public Stratadon copy() {
        return new Stratadon(this);
    }
}
