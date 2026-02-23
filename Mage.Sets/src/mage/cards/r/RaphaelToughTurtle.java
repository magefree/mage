package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelToughTurtle extends CardImpl {

    public RaphaelToughTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Alliance -- Whenever another creature you control enters, Raphael deals 1 damage to target opponent.
        Ability ability = new AllianceAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RaphaelToughTurtle(final RaphaelToughTurtle card) {
        super(card);
    }

    @Override
    public RaphaelToughTurtle copy() {
        return new RaphaelToughTurtle(this);
    }
}
