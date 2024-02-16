package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author L_J
 */
public final class EverdawnChampion extends CardImpl {

    public EverdawnChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prevent all combat damage that would be dealt to Everdawn Champion.
        this.addAbility(new SimpleStaticAbility(new PreventCombatDamageToSourceEffect(Duration.WhileOnBattlefield)));
    }

    private EverdawnChampion(final EverdawnChampion card) {
        super(card);
    }

    @Override
    public EverdawnChampion copy() {
        return new EverdawnChampion(this);
    }
}
