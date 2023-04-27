package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class MyrConvert extends CardImpl {

    public MyrConvert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Toxic 1
        this.addAbility(new ToxicAbility(1));

        // {T}, Pay 2 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);
    }

    private MyrConvert(final MyrConvert card) {
        super(card);
    }

    @Override
    public MyrConvert copy() {
        return new MyrConvert(this);
    }
}
