
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class KavuScout extends CardImpl {

    public KavuScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Domain - Kavu Scout gets +1/+0 for each basic land type among lands you control.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(DomainValue.REGULAR, StaticValue.get(0), Duration.WhileOnBattlefield));
        ability.setAbilityWord(AbilityWord.DOMAIN);
        this.addAbility(ability.addHint(DomainHint.instance));
    }

    private KavuScout(final KavuScout card) {
        super(card);
    }

    @Override
    public KavuScout copy() {
        return new KavuScout(this);
    }
}
