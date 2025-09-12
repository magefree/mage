package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SourceDealsNoncombatDamageToOpponentTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class NivMizzetVisionary extends CardImpl {

    public NivMizzetVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You have no maximum hand size.
        Effect effect = new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        this.addAbility(new SimpleStaticAbility(effect));

        // Whenever a source you control deals noncombat damage to an opponent, you draw that many cards.
        this.addAbility(new SourceDealsNoncombatDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(SavedDamageValue.MANY, true)));
    }

    private NivMizzetVisionary(final NivMizzetVisionary card) {
        super(card);
    }

    @Override
    public NivMizzetVisionary copy() {
        return new NivMizzetVisionary(this);
    }
}
