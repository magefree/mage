
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
/**
 *
 * @author TheElk801
 */
public final class VodalianArcanist extends CardImpl {

    public VodalianArcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {C}. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, 
                new mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder()));
    }

    public VodalianArcanist(final VodalianArcanist card) {
        super(card);
    }

    @Override
    public VodalianArcanist copy() {
        return new VodalianArcanist(this);
    }
}
