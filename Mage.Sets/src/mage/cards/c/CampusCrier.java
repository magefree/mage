package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CampusCrier extends CardImpl {

    public CampusCrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {1}, Exile this card from your graveyard: Empower Jace 2.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new EmpowerJaceEffect(2),
            new ManaCostsImpl<>("{1}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private CampusCrier(final CampusCrier card) {
        super(card);
    }

    @Override
    public CampusCrier copy() {
        return new CampusCrier(this);
    }
}
