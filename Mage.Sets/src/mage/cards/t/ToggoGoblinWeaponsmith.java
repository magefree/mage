package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RockToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToggoGoblinWeaponsmith extends CardImpl {

    public ToggoGoblinWeaponsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a land enters the battlefield under your control, create an artifact equipment token named Rock wih "Equipped creature has '{1}, {T}, Sacrifice Rock: This creature deals 2 damage to any target'" and equip {1}.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new RockToken())));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ToggoGoblinWeaponsmith(final ToggoGoblinWeaponsmith card) {
        super(card);
    }

    @Override
    public ToggoGoblinWeaponsmith copy() {
        return new ToggoGoblinWeaponsmith(this);
    }
}
