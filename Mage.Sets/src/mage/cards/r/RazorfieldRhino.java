package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author Loki
 */
public final class RazorfieldRhino extends CardImpl {

    public RazorfieldRhino(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Metalcraft — Razorfield Rhino gets +2/+2 as long as you control three or more artifacts.
        ContinuousEffect effect1 = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect1, MetalcraftCondition.instance, "{this} gets +2/+2 as long as you control three or more artifacts"))
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance));
    }

    private RazorfieldRhino(final RazorfieldRhino card) {
        super(card);
    }

    @Override
    public RazorfieldRhino copy() {
        return new RazorfieldRhino(this);
    }

}
