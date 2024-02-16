
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.KorAllyToken;

/**
 *
 * @author LevelX2
 */
public final class CaptainsClaws extends CardImpl {

    public CaptainsClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 0)));
        // Whenever equipped creature attacks, create a 1/1 white Kor Ally creature token tapped and attacking.
        this.addAbility(new AttacksAttachedTriggeredAbility(new CreateTokenEffect(new KorAllyToken(), 1, true, true)));
        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), false));
    }

    private CaptainsClaws(final CaptainsClaws card) {
        super(card);
    }

    @Override
    public CaptainsClaws copy() {
        return new CaptainsClaws(this);
    }
}

