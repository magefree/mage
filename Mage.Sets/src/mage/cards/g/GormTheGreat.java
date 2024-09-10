
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author L_J
 */
public final class GormTheGreat extends CardImpl {

    public GormTheGreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Partner with Virtus the Veiled (When this creature enters the battlefield, target player may put Virtus into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Virtus the Veiled", true));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Gorm the Great must be blocked if able, and Gorm must be blocked by two or more creatures if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield, 2)
                .setText("{this} must be blocked if able, and {this} must be blocked by two or more creatures if able")));
    }

    private GormTheGreat(final GormTheGreat card) {
        super(card);
    }

    @Override
    public GormTheGreat copy() {
        return new GormTheGreat(this);
    }
}
