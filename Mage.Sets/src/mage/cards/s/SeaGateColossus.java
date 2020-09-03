package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public final class SeaGateColossus extends CardImpl {

    public SeaGateColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.GOLEM);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // This spell costs {1} less for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance));
    }

    private SeaGateColossus(final SeaGateColossus card) {
        super(card);
    }

    @Override
    public SeaGateColossus copy() {
        return new SeaGateColossus(this);
    }
}
