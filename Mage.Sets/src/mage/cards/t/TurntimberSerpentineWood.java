package mage.cards.t;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurntimberSerpentineWood extends CardImpl {

    public TurntimberSerpentineWood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // As Turntimber, Serpentine Wood enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private TurntimberSerpentineWood(final TurntimberSerpentineWood card) {
        super(card);
    }

    @Override
    public TurntimberSerpentineWood copy() {
        return new TurntimberSerpentineWood(this);
    }
}
