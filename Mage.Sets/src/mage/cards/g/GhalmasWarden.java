package mage.cards.g;

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
public final class GhalmasWarden extends CardImpl {

    private static final String rule = "{this} gets +2/+2 as long as you control three or more artifacts";

    public GhalmasWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Metalcraft — Ghalma’s Warden gets +2/+2 as long as you control three or more artifacts.
        ContinuousEffect boostSource = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, MetalcraftCondition.instance, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private GhalmasWarden(final GhalmasWarden card) {
        super(card);
    }

    @Override
    public GhalmasWarden copy() {
        return new GhalmasWarden(this);
    }

}
