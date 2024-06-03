package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.DemonstrateAbility;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author grimreap124
 */
public final class SilverquillLecturer extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public SilverquillLecturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{4}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Creature spells you cast have demonstrate.
        this.addAbility(
                new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new DemonstrateAbility(), filter)
                        .setText("Creature spells you cast have demonstrate.")));
    }

    private SilverquillLecturer(final SilverquillLecturer card) {
        super(card);
    }

    @Override
    public SilverquillLecturer copy() {
        return new SilverquillLecturer(this);
    }
}
