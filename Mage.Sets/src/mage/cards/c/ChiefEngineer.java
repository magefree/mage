
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactSpell;

/**
 *
 * @author LevelX2
 */
public final class ChiefEngineer extends CardImpl {

    public ChiefEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Artifact spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledSpellsEffect(new ConvokeAbility(), new FilterArtifactSpell("Artifact spells you cast"))));

    }

    public ChiefEngineer(final ChiefEngineer card) {
        super(card);
    }

    @Override
    public ChiefEngineer copy() {
        return new ChiefEngineer(this);
    }
}
