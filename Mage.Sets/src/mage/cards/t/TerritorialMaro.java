package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerritorialMaro extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(DomainValue.REGULAR, 2);

    public TerritorialMaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Domain — Territorial Maro's power and toughness are each equal to twice the number of basic land types among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue)
                .setText("{this}'s power and toughness are each equal to twice the number of basic land types among lands you control")
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private TerritorialMaro(final TerritorialMaro card) {
        super(card);
    }

    @Override
    public TerritorialMaro copy() {
        return new TerritorialMaro(this);
    }
}
