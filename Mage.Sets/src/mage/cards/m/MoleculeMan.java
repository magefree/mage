package mage.cards.m;

import mage.MageInt;
import mage.abilities.effects.common.continuous.GainMiracleAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class MoleculeMan extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland cards");

    public MoleculeMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Nonland cards in your hand have miracle {0}.
        this.addAbility(GainMiracleAbilityAllEffect.makeAbility(filter, "{0}"));
    }

    private MoleculeMan(final MoleculeMan card) {
        super(card);
    }

    @Override
    public MoleculeMan copy() {
        return new MoleculeMan(this);
    }
}
