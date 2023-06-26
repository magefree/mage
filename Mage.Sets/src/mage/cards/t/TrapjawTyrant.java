package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TrapjawTyrant extends CardImpl {

    public TrapjawTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Enrage — Whenever Trapjaw Tyrant is dealt damage, exile target creature an opponent controls until Trapjaw Tyrant leaves the battlefield.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new ExileUntilSourceLeavesEffect(), false, true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private TrapjawTyrant(final TrapjawTyrant card) {
        super(card);
    }

    @Override
    public TrapjawTyrant copy() {
        return new TrapjawTyrant(this);
    }
}
