
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TrooperToken;

/**
 *
 * @author Styxo
 */
public final class LAATGunship extends CardImpl {

    public LAATGunship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When LAAT Gunship attacks, create a 1/1 white Trooper creature token on to battlefield tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TrooperToken(), 1, true, true), false));

        // {W}: LAAT Gunship gains spaceflight until the end of turn. Activate this ability only as a sorcery
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(SpaceflightAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{W}")));

    }

    private LAATGunship(final LAATGunship card) {
        super(card);
    }

    @Override
    public LAATGunship copy() {
        return new LAATGunship(this);
    }
}
