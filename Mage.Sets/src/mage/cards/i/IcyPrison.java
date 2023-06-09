
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IcyPrison extends CardImpl {

    public IcyPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");


        // When Icy Prison enters the battlefield, exile target creature.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);

        // At the beginning of your upkeep, sacrifice Icy Prison unless any player pays {3}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoUnlessAnyPlayerPaysEffect(new SacrificeSourceEffect(), new GenericManaCost(3)), TargetController.YOU, false));

        // When Icy Prison leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false));

    }

    private IcyPrison(final IcyPrison card) {
        super(card);
    }

    @Override
    public IcyPrison copy() {
        return new IcyPrison(this);
    }
}
