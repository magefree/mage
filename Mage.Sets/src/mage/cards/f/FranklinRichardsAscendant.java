package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FranklinRichardsAscendant extends CardImpl {

    public FranklinRichardsAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, discover 6.
        Ability ability = new BeginningOfCombatTriggeredAbility(new DiscoverEffect(6))
            .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance);
        this.addAbility(ability);
    }

    private FranklinRichardsAscendant(final FranklinRichardsAscendant card) {
        super(card);
    }

    @Override
    public FranklinRichardsAscendant copy() {
        return new FranklinRichardsAscendant(this);
    }
}
