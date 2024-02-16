package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jerekwilson
 */
public final class JaggedPoppet extends CardImpl {

    public JaggedPoppet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Jagged Poppet is dealt damage, discard that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new DiscardControllerEffect(SavedDamageValue.MANY), false));

        // Hellbent - Whenever Jagged Poppet deals combat damage to a player, if you have no cards in hand, that player discards cards equal to the damage.
        Ability hellbentAbility = new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(SavedDamageValue.MANY), false, true),
                HellbentCondition.instance,
                "Whenever {this} deals combat damage to a player, if you have no cards in hand, that player discards cards equal to the damage.");
        hellbentAbility.setAbilityWord(AbilityWord.HELLBENT);
        this.addAbility(hellbentAbility);
    }

    private JaggedPoppet(final JaggedPoppet card) {
        super(card);
    }

    @Override
    public JaggedPoppet copy() {
        return new JaggedPoppet(this);
    }
}
