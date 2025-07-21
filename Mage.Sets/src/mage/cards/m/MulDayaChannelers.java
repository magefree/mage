
package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TopLibraryCardTypeCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.PlayWithTheTopCardRevealedEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class MulDayaChannelers extends CardImpl {

    private static final String rule1 = "As long as the top card of your library is a creature card, {this} gets +3/+3";

    public MulDayaChannelers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.SHAMAN);

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Play with the top card of your library revealed.
        this.addAbility(new SimpleStaticAbility(new PlayWithTheTopCardRevealedEffect()));

        // As long as the top card of your library is a creature card, Mul Daya Channelers gets +3/+3.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(3, 3, Duration.WhileOnBattlefield), new TopLibraryCardTypeCondition(CardType.CREATURE), rule1);
        this.addAbility(new SimpleStaticAbility(effect));

        // As long as the top card of your library is a land card, Mul Daya Channelers has "T: Add two mana of any one color."
        SimpleManaAbility manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        effect = new ConditionalContinuousEffect(new GainAbilitySourceEffect(manaAbility, Duration.WhileOnBattlefield),
                new TopLibraryCardTypeCondition(CardType.LAND),
                "As long as the top card of your library is a land card, {this} has \"{T}: Add two mana of any one color.\"");
        this.addAbility(new SimpleStaticAbility(effect));

    }

    private MulDayaChannelers(final MulDayaChannelers card) {
        super(card);
    }

    @Override
    public MulDayaChannelers copy() {
        return new MulDayaChannelers(this);
    }
}
