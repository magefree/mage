package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RamunapExcavator extends CardImpl {

    public RamunapExcavator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLands()));
    }

    private RamunapExcavator(final RamunapExcavator card) {
        super(card);
    }

    @Override
    public RamunapExcavator copy() {
        return new RamunapExcavator(this);
    }
}
