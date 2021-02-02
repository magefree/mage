
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantHaveCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MelirasKeepers extends CardImpl {

    public MelirasKeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Melira's Keepers can't have counters put on it
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantHaveCountersSourceEffect()));
    }

    private MelirasKeepers(final MelirasKeepers card) {
        super(card);
    }

    @Override
    public MelirasKeepers copy() {
        return new MelirasKeepers(this);
    }

}
