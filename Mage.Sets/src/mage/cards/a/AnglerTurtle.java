package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class AnglerTurtle extends CardImpl {

    public AnglerTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Creatures your opponents control attack each combat if able
        this.addAbility(new SimpleStaticAbility(new AttacksIfAbleAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES)));
    }

    private AnglerTurtle(final AnglerTurtle card) {
        super(card);
    }

    @Override
    public AnglerTurtle copy() {
        return new AnglerTurtle(this);
    }
}
