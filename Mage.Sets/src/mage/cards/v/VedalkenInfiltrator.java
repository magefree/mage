package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VedalkenInfiltrator extends CardImpl {

    public VedalkenInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vedalken Infiltrator can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Metalcraft — Vedalken Infiltrator gets +1/+0 as long as you control three or more artifacts.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                MetalcraftCondition.instance, "{this} gets +1/+0 as long as you control three or more artifacts"
        )).setAbilityWord(AbilityWord.METALCRAFT).addHint(MetalcraftHint.instance));
    }

    private VedalkenInfiltrator(final VedalkenInfiltrator card) {
        super(card);
    }

    @Override
    public VedalkenInfiltrator copy() {
        return new VedalkenInfiltrator(this);
    }
}
