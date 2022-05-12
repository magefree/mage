package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author North
 */
public final class BleakCovenVampires extends CardImpl {

    private static final String effectText = "When {this} enters the battlefield, if you control three or more artifacts, target player loses 4 life and you gain 4 life.";

    public BleakCovenVampires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        //<i>Metalcraft</i> &mdash; When Bleak Coven Vampires enters the battlefield, if you control three or more artifacts, target player loses 4 life and you gain 4 life.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(4), false);
        ability.addEffect(new GainLifeEffect(4));
        Target target = new TargetPlayer();
        ability.addTarget(target);

        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, MetalcraftCondition.instance, effectText)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance)
        );
    }

    private BleakCovenVampires(final BleakCovenVampires card) {
        super(card);
    }

    @Override
    public BleakCovenVampires copy() {
        return new BleakCovenVampires(this);
    }
}
