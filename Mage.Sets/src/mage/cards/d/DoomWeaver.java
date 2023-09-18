package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoomWeaver extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public DoomWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(8);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Doom Weaver is paired with another creature, each of those creatures has "When this creature dies, draw cards equal to its power."
        this.addAbility(new SimpleStaticAbility(new GainAbilityPairedEffect(new DiesSourceTriggeredAbility(
                new DrawCardSourceControllerEffect(xValue).setText("draw cards equal to its power")
        ).setTriggerPhrase("When this creature dies, "), "As long as {this} is paired with another creature, " +
                "each of those creatures has \"When this creature dies, draw cards equal to its power.\"")));
    }

    private DoomWeaver(final DoomWeaver card) {
        super(card);
    }

    @Override
    public DoomWeaver copy() {
        return new DoomWeaver(this);
    }
}
