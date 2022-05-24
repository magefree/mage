package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class AuspiciousAncestor extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public AuspiciousAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Auspicious Ancestor dies, you gain 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(3), false));
        // Whenever a player casts a white spell, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1), new ManaCostsImpl<>("{1}")), filter, false));
    }

    private AuspiciousAncestor(final AuspiciousAncestor card) {
        super(card);
    }

    @Override
    public AuspiciousAncestor copy() {
        return new AuspiciousAncestor(this);
    }
}
