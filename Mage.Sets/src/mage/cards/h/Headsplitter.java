package mage.cards.h;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AssassinMenaceToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Headsplitter extends CardImpl {

    public Headsplitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Headsplitter enters the battlefield, create a 1/1 black Assassin creature token with menace, then attach Headsplitter to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new AssassinMenaceToken())));

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private Headsplitter(final Headsplitter card) {
        super(card);
    }

    @Override
    public Headsplitter copy() {
        return new Headsplitter(this);
    }
}
