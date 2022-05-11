package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BorosReckoner extends CardImpl {

    public BorosReckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/W}{R/W}{R/W}");
        this.subtype.add(SubType.MINOTAUR, SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Boros Reckoner is dealt damage, it deals that much damage to any target.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new DamageTargetEffect(SavedDamageValue.MUCH, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {R/W}: Boros Reckoner gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R/W}")));
    }

    private BorosReckoner(final BorosReckoner card) {
        super(card);
    }

    @Override
    public BorosReckoner copy() {
        return new BorosReckoner(this);
    }
}
