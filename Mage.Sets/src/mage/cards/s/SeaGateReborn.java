package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeaGateReborn extends CardImpl {

    public SeaGateReborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // As Sea Gate, Reborn enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private SeaGateReborn(final SeaGateReborn card) {
        super(card);
    }

    @Override
    public SeaGateReborn copy() {
        return new SeaGateReborn(this);
    }
}
