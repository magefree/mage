package mage.cards.e;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmeriaShatteredSkyclave extends CardImpl {

    public EmeriaShatteredSkyclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // As Emeria, Shattered Skyclave enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private EmeriaShatteredSkyclave(final EmeriaShatteredSkyclave card) {
        super(card);
    }

    @Override
    public EmeriaShatteredSkyclave copy() {
        return new EmeriaShatteredSkyclave(this);
    }
}
