package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
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
public final class IlysianCaryatid extends CardImpl {

    public IlysianCaryatid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add one mana of any color. If you control a creature with power 4 or greater, add two mana of any one color instead.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new AddManaOfAnyColorEffect(2),
                        new AddManaOfAnyColorEffect(1),
                        FerociousCondition.instance,
                        "Add one mana of any color. If you control a creature with power 4 or greater, add two mana of any one color instead"
                ), new TapSourceCost()));
    }

    private IlysianCaryatid(final IlysianCaryatid card) {
        super(card);
    }

    @Override
    public IlysianCaryatid copy() {
        return new IlysianCaryatid(this);
    }
}
