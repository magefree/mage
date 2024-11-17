package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToweringWaveMystic extends CardImpl {

    public ToweringWaveMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Towering-Wave Mystic deals damage, target player mills that many cards.
        this.addAbility(new DealsDamageSourceTriggeredAbility(new MillCardsTargetEffect(SavedDamageValue.MANY)));
    }

    private ToweringWaveMystic(final ToweringWaveMystic card) {
        super(card);
    }

    @Override
    public ToweringWaveMystic copy() {
        return new ToweringWaveMystic(this);
    }
}
