

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class EzurisBrigade extends CardImpl {
    private static final String text = "<i>Metalcraft</i> &mdash; As long as you control three or more artifacts, Ezuri's Brigade gets +4/+4 and has trample";

    public EzurisBrigade (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        ContinuousEffect boostSource = new BoostSourceEffect(4, 4, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, MetalcraftCondition.instance, text);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield), MetalcraftCondition.instance, ""));
        this.addAbility(ability);
    }

    public EzurisBrigade (final EzurisBrigade card) {
        super(card);
    }

    @Override
    public EzurisBrigade copy() {
        return new EzurisBrigade(this);
    }

}
