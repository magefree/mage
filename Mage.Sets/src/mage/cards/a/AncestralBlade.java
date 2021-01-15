package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncestralBlade extends CardImpl {

    public AncestralBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Ancestral Blade enters the battlefield, create a 1/1 white Soldier creature token, then attach Ancestral Blade to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new SoldierToken())));

        // Equipped creature get +1/+1
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private AncestralBlade(final AncestralBlade card) {
        super(card);
    }

    @Override
    public AncestralBlade copy() {
        return new AncestralBlade(this);
    }
}
