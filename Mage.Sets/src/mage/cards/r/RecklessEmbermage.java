package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetAndSelfEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class RecklessEmbermage extends CardImpl {

    public RecklessEmbermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: Reckless Embermage deals 1 damage to any target and 1 damage to itself.
        Ability ability = new SimpleActivatedAbility(new DamageTargetAndSelfEffect(1), new ManaCostsImpl<>("{1}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RecklessEmbermage(final RecklessEmbermage card) {
        super(card);
    }

    @Override
    public RecklessEmbermage copy() {
        return new RecklessEmbermage(this);
    }
}
