package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class IymrithDesertDoom extends CardImpl {

    public IymrithDesertDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Iymrith, Desert Doom has ward {4} as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new WardAbility(new GenericManaCost(4)), Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED, "{this} has ward {4} as long as it's untapped"
        )));

        // Whenever Iymrith deals combat damage to a player, draw a card. Then if you have fewer than three cards in hand, draw cards equal to the difference.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new DrawCardsEqualToDifferenceEffect(3)
                .concatBy("Then if you have fewer than three cards in hand,"));
        this.addAbility(ability);
    }

    private IymrithDesertDoom(final IymrithDesertDoom card) {
        super(card);
    }

    @Override
    public IymrithDesertDoom copy() {
        return new IymrithDesertDoom(this);
    }
}
