package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BloodhallPriest extends CardImpl {

    public BloodhallPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Bloodhall Priest enters the battlefield or attacks, if you have no cards in hand, Bloodhall Priest deals 2 damage to any target.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageTargetEffect(2))
                .withInterveningIf(HellbentCondition.instance);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Madness {1}{B}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{1}{B}{R}")));
    }

    private BloodhallPriest(final BloodhallPriest card) {
        super(card);
    }

    @Override
    public BloodhallPriest copy() {
        return new BloodhallPriest(this);
    }
}
