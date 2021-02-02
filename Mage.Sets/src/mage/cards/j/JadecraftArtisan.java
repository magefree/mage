
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JayDi85
 */
public final class JadecraftArtisan extends CardImpl {

    public JadecraftArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Jadecraft Artisan enters the battlefield, target creature gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn),false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private JadecraftArtisan(final JadecraftArtisan card) {
        super(card);
    }

    @Override
    public JadecraftArtisan copy() {
        return new JadecraftArtisan(this);
    }
}