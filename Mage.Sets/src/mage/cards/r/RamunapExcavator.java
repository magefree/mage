package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.PlayLandsFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RamunapExcavator extends CardImpl {

    public RamunapExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayLandsFromGraveyardControllerEffect()));
    }

    private RamunapExcavator(final RamunapExcavator card) {
        super(card);
    }

    @Override
    public RamunapExcavator copy() {
        return new RamunapExcavator(this);
    }
}