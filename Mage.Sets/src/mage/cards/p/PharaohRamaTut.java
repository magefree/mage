package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PharaohRamaTut extends CardImpl {

    public PharaohRamaTut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever you cast a noncreature spell, Pharaoh Rama-Tut connives.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new ConniveSourceEffect("{this}"),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private PharaohRamaTut(final PharaohRamaTut card) {
        super(card);
    }

    @Override
    public PharaohRamaTut copy() {
        return new PharaohRamaTut(this);
    }
}
