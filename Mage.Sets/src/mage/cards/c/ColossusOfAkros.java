
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class ColossusOfAkros extends CardImpl {

    public ColossusOfAkros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{8}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // indestructible.
        this.addAbility(IndestructibleAbility.getInstance());
        // {10}: Monstrosity 10.
        this.addAbility(new MonstrosityAbility("{10}", 10));
        // As long as Colossus of Akros is monstrous, it has trample and can attack as though it didn't have defender.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.instance,
                "As long as {this} is monstrous, it has trample"));
        Effect effect = new ConditionalAsThoughEffect(new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                MonstrousCondition.instance);
        effect.setText("and can attack as though it didn't have defender");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ColossusOfAkros(final ColossusOfAkros card) {
        super(card);
    }

    @Override
    public ColossusOfAkros copy() {
        return new ColossusOfAkros(this);
    }
}
