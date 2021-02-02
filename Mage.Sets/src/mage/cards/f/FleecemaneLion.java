
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class FleecemaneLion extends CardImpl {

    public FleecemaneLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {3}{G}{W}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{3}{G}{W}", 1));
        // As long as Fleecemane Lion is monstrous, it has hexproof and indestructible.
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.instance,
                "As long as {this} is monstrous, it has hexproof");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.instance,
                "and indestructible");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FleecemaneLion(final FleecemaneLion card) {
        super(card);
    }

    @Override
    public FleecemaneLion copy() {
        return new FleecemaneLion(this);
    }
}
