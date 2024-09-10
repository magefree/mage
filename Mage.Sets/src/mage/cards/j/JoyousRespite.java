package mage.cards.j;

import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class JoyousRespite extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public JoyousRespite (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");
        this.subtype.add(SubType.ARCANE);


        // You gain 1 life for each land you control.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("you gain 1 life for each land you control"));
    }

    private JoyousRespite(final JoyousRespite card) {
        super(card);
    }

    @Override
    public JoyousRespite copy() {
        return new JoyousRespite(this);
    }

}
