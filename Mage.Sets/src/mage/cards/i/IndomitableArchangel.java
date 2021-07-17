package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class IndomitableArchangel extends CardImpl {

    private static final String rule = "Artifacts you control have shroud as long as you control three or more artifacts.";

    private static final FilterPermanent filter = new FilterPermanent("Artifacts");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public IndomitableArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Metalcraft — Artifacts you control have shroud as long as you control three or more artifacts. (An artifact with shroud can’t be the target of spells or abilities.)
        ContinuousEffect gainAbilityEffect = new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(gainAbilityEffect, MetalcraftCondition.instance, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private IndomitableArchangel(final IndomitableArchangel card) {
        super(card);
    }

    @Override
    public IndomitableArchangel copy() {
        return new IndomitableArchangel(this);
    }

}
