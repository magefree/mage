package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OteclanLandmark extends CardImpl {

    public OteclanLandmark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");
        this.secondSideCardClazz = mage.cards.o.OteclanLevitator.class;

        // When Oteclan Landmark enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // Craft with artifact {2}{W}
        this.addAbility(new CraftAbility("{2}{W}"));
    }

    private OteclanLandmark(final OteclanLandmark card) {
        super(card);
    }

    @Override
    public OteclanLandmark copy() {
        return new OteclanLandmark(this);
    }
}
