package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class AuriokSunchaser extends CardImpl {

    protected static String effect1Text = "<i>Metalcraft</i> &mdash; As long as you control three or more artifacts, Auriok Sunchaser gets +2/+2";
    protected static String effect2Text = "<i>Metalcraft</i> &mdash; As long as you control three or more artifacts, Auriok Sunchaser has flying";

    public AuriokSunchaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        ContinuousEffect effect1 = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(effect1, MetalcraftCondition.instance, effect1Text))
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );

        ContinuousEffect effect2 = new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(effect2, MetalcraftCondition.instance, effect2Text))
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private AuriokSunchaser(final AuriokSunchaser card) {
        super(card);
    }

    @Override
    public AuriokSunchaser copy() {
        return new AuriokSunchaser(this);
    }
}
