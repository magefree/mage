package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FiredrinkerSatyr extends CardImpl {

    public FiredrinkerSatyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Firedrinker Satyr is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new DamageControllerEffect(SavedDamageValue.MUCH, "it"), false));
        // {1}{R}: Firedrinker Satyr gets +1/+0 until end of turn and deals 1 damage to you.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new DamageControllerEffect(1, "and"));
        this.addAbility(ability);
    }

    private FiredrinkerSatyr(final FiredrinkerSatyr card) {
        super(card);
    }

    @Override
    public FiredrinkerSatyr copy() {
        return new FiredrinkerSatyr(this);
    }
}
