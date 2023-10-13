package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class OasisRitualist extends CardImpl {

    public OasisRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
        // {T}, Exert Oasis Ritualist: Add two mana of any one color to your manna pool.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(2).setText("Add two mana of any one color. <i>(An exerted creature won't untap during your next untap step.)</i>"),
                 new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        this.addAbility(ability);
    }

    private OasisRitualist(final OasisRitualist card) {
        super(card);
    }

    @Override
    public OasisRitualist copy() {
        return new OasisRitualist(this);
    }
}
