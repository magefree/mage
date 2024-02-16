package mage.cards.d;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DrownyardTemple extends CardImpl {

    public DrownyardTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}: Return Drownyard Temple from your graveyard to the battlefield tapped.
        this.addAbility(new SimpleActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true, false), new GenericManaCost(3)));
    }

    private DrownyardTemple(final DrownyardTemple card) {
        super(card);
    }

    @Override
    public DrownyardTemple copy() {
        return new DrownyardTemple(this);
    }
}
