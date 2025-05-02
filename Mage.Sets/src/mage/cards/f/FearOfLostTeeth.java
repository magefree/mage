package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfLostTeeth extends CardImpl {

    public FearOfLostTeeth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Fear of Lost Teeth dies, it deals 1 damage to any target and you gain 1 life.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FearOfLostTeeth(final FearOfLostTeeth card) {
        super(card);
    }

    @Override
    public FearOfLostTeeth copy() {
        return new FearOfLostTeeth(this);
    }
}
