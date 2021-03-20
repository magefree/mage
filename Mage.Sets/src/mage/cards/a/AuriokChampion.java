
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class AuriokChampion extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AuriokChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from black and from red
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED));
        
        // Whenever another creature enters the battlefield, you may gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), filter, true));

    }

    private AuriokChampion(final AuriokChampion card) {
        super(card);
    }

    @Override
    public AuriokChampion copy() {
        return new AuriokChampion(this);
    }
}
