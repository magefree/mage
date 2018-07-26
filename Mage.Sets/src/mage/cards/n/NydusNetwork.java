package mage.cards.n;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class NydusNetwork extends CardImpl {

    public NydusNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Nydus Network enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Nydus Network enters the battlefield, target creature you control can't be blocked this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {T}: Add {B} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new TapSourceCost()));
    }

    public NydusNetwork(final NydusNetwork card) {
        super(card);
    }

    @Override
    public NydusNetwork copy() {
        return new NydusNetwork(this);
    }
}
