package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.hint.common.EnduringStoryHint;
import mage.abilities.keyword.StoriedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class DainLordOfTheIronHills extends CardImpl {

    public DainLordOfTheIronHills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Storied
        this.addAbility(new StoriedAbility());

        // As long as you have an enduring story, creatures can't attack you unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(new ConditionalReplacementEffect(
            new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield,
                new ManaCostsImpl<>("{1}")
            ),
            EnduringStoryCondition.instance
        ).setText("as long as you have an enduring story, creatures can't attack you unless their controller pays {1} for each of those creatures"))
        .addHint(EnduringStoryHint.instance));
    }

    private DainLordOfTheIronHills(final DainLordOfTheIronHills card) {
        super(card);
    }

    @Override
    public DainLordOfTheIronHills copy() {
        return new DainLordOfTheIronHills(this);
    }
}
