package mage.cards.a;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class AutoTurret extends CardImpl {

    public AutoTurret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Auto-Turret enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost()));

        // {3}: Turn Auto-Turret face down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesFaceDownCreatureEffect(Duration.Custom, BecomesFaceDownCreatureEffect.FaceDownType.MANUAL)
                        .setText("Turn {this} face down"),
                new GenericManaCost(3)));
    }

    public AutoTurret(final AutoTurret card) {
        super(card);
    }

    @Override
    public AutoTurret copy() {
        return new AutoTurret(this);
    }
}
