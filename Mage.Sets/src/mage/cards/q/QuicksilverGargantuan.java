
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.CopyApplier;

/**
 * @author ayratn
 */
public final class QuicksilverGargantuan extends CardImpl {

    public QuicksilverGargantuan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        Ability ability = new EntersBattlefieldAbility(new CopyPermanentEffect(new QuicksilverGargantuanCopyApplier()),
                "You may have {this} enter the battlefield as a copy of any creature on the battlefield, except it's 7/7");
        this.addAbility(ability);
    }

    public QuicksilverGargantuan(final QuicksilverGargantuan card) {
        super(card);
    }

    @Override
    public QuicksilverGargantuan copy() {
        return new QuicksilverGargantuan(this);
    }
}

class QuicksilverGargantuanCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
        blueprint.removePTCDA();
        blueprint.getPower().modifyBaseValue(7);
        blueprint.getToughness().modifyBaseValue(7);
        return true;
    }
}
