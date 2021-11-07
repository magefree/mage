package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyreSpawn extends CardImpl {

    public PyreSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // When Pyre Spawn dies, it deals 3 damage to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private PyreSpawn(final PyreSpawn card) {
        super(card);
    }

    @Override
    public PyreSpawn copy() {
        return new PyreSpawn(this);
    }
}
