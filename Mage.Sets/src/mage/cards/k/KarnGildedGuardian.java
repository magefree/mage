package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ColorsAmongControlledPermanentsCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KarnGildedGuardian extends CardImpl {

    public KarnGildedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2/W}{2/U}{2/B}{2/R}{2/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Karn enters, draw a card for each color among other artifacts you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DrawCardSourceControllerEffect(ColorsAmongControlledPermanentsCount.OTHER_ARTIFACTS)
        ).addHint(ColorsAmongControlledPermanentsCount.OTHER_ARTIFACTS.getHint()));
    }

    private KarnGildedGuardian(final KarnGildedGuardian card) {
        super(card);
    }

    @Override
    public KarnGildedGuardian copy() {
        return new KarnGildedGuardian(this);
    }
}
