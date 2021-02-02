package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author NinthWorld
 */
public final class CorellianGunship extends CardImpl {

    public CorellianGunship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // When Corellian Gunship enters the battlefield, it deals 1 damage to target player or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private CorellianGunship(final CorellianGunship card) {
        super(card);
    }

    @Override
    public CorellianGunship copy() {
        return new CorellianGunship(this);
    }
}
