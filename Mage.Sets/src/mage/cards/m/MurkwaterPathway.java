package mage.cards.m;

import java.util.UUID;

import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class MurkwaterPathway extends CardImpl {

    public MurkwaterPathway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.nightCard = true;
        this.transformable = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private MurkwaterPathway(final MurkwaterPathway card) {
        super(card);
    }

    @Override
    public MurkwaterPathway copy() {
        return new MurkwaterPathway(this);
    }
}
