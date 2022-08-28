package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YavimayaSojourner extends CardImpl {

    public YavimayaSojourner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Domain - This spell costs {1} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(DomainValue.REGULAR)
        ).setAbilityWord(AbilityWord.DOMAIN).addHint(DomainHint.instance));
    }

    private YavimayaSojourner(final YavimayaSojourner card) {
        super(card);
    }

    @Override
    public YavimayaSojourner copy() {
        return new YavimayaSojourner(this);
    }
}
