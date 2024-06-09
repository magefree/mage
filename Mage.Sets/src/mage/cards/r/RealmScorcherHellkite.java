package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RealmScorcherHellkite extends CardImpl {

    public RealmScorcherHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Bargain
        this.addAbility(new BargainAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Realm-Scorcher Hellkite enters the battlefield, if it was bargained, add four mana in any combination of colors.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new AddManaInAnyCombinationEffect(4)),
                BargainedCondition.instance,
                "When {this} enters the battlefield, if it was bargained, add four mana in any combination of colors."
        ));

        // {1}{R}: Realm-Scorcher Hellkite deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RealmScorcherHellkite(final RealmScorcherHellkite card) {
        super(card);
    }

    @Override
    public RealmScorcherHellkite copy() {
        return new RealmScorcherHellkite(this);
    }
}
