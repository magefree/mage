
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public final class BowerPassage extends CardImpl {

    public BowerPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // Creatures with flying can't block creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BowerPassageEffect()));
    }

    public BowerPassage(final BowerPassage card) {
        super(card);
    }

    @Override
    public BowerPassage copy() {
        return new BowerPassage(this);
    }
}

class BowerPassageEffect extends RestrictionEffect {

    BowerPassageEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with flying can't block creatures you control";
    }

    BowerPassageEffect(final BowerPassageEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return true;
    }

    @Override
    public BowerPassageEffect copy() {
        return new BowerPassageEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (attacker != null && attacker.isControlledBy(source.getControllerId()) && blocker.getAbilities().contains(FlyingAbility.getInstance())) {
            return false;
        }
        return true;
    }

}
