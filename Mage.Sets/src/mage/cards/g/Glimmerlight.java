package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GlimmerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Glimmerlight extends CardImpl {

    public Glimmerlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Glimmerlight enters, create a 1/1 white Glimmer enchantment creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GlimmerToken())));

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private Glimmerlight(final Glimmerlight card) {
        super(card);
    }

    @Override
    public Glimmerlight copy() {
        return new Glimmerlight(this);
    }
}
