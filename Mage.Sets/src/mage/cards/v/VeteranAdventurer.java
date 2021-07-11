package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.continuous.HasSubtypesSourceEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranAdventurer extends CardImpl {

    public VeteranAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Veteran Adventurer is also a Cleric, Rogue, Warrior, and Wizard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new HasSubtypesSourceEffect(
                SubType.CLERIC, SubType.ROGUE, SubType.WARRIOR, SubType.WIZARD
        )));

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance));

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private VeteranAdventurer(final VeteranAdventurer card) {
        super(card);
    }

    @Override
    public VeteranAdventurer copy() {
        return new VeteranAdventurer(this);
    }
}
