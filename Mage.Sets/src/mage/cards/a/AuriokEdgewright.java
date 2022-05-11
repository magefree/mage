package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class AuriokEdgewright extends CardImpl {

    private static final String effectText = "{this} has double strike as long as you control three or more artifacts.";

    public AuriokEdgewright(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        ContinuousEffect effect = new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(effect, MetalcraftCondition.instance, effectText))
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private AuriokEdgewright(final AuriokEdgewright card) {
        super(card);
    }

    @Override
    public AuriokEdgewright copy() {
        return new AuriokEdgewright(this);
    }
}
