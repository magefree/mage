
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class LinvalaKeeperOfSilence extends CardImpl {

    public LinvalaKeeperOfSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Activated abilities of creatures your opponents control can't be activated.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LinvalaKeeperOfSilenceCantActivateEffect()));
    }

    public LinvalaKeeperOfSilence(final LinvalaKeeperOfSilence card) {
        super(card);
    }

    @Override
    public LinvalaKeeperOfSilence copy() {
        return new LinvalaKeeperOfSilence(this);
    }
}

class LinvalaKeeperOfSilenceCantActivateEffect extends RestrictionEffect {

    public LinvalaKeeperOfSilenceCantActivateEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of creatures your opponents control can't be activated";
    }

    public LinvalaKeeperOfSilenceCantActivateEffect(final LinvalaKeeperOfSilenceCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature()
                && game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game) {
        return false;
    }

    @Override
    public LinvalaKeeperOfSilenceCantActivateEffect copy() {
        return new LinvalaKeeperOfSilenceCantActivateEffect(this);
    }

}
