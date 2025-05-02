package mage.cards.m;

import mage.Mana;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MuragandaRaceway extends CardImpl {

    public MuragandaRaceway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Max speed -- {T}: Add {C}{C}.
        this.addAbility(new MaxSpeedAbility(
                new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost())
        ));
    }

    private MuragandaRaceway(final MuragandaRaceway card) {
        super(card);
    }

    @Override
    public MuragandaRaceway copy() {
        return new MuragandaRaceway(this);
    }
}
