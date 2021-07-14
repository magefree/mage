
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SpitefulBully extends CardImpl {

    public SpitefulBully(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, Spiteful Bully deals 3 damage to target creature you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(3), TargetController.YOU, false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SpitefulBully(final SpitefulBully card) {
        super(card);
    }

    @Override
    public SpitefulBully copy() {
        return new SpitefulBully(this);
    }
}
