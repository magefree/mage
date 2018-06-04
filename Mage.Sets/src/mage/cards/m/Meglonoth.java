
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Meglonoth extends CardImpl {

    public Meglonoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Meglonoth blocks a creature, Meglonoth deals damage to that creature's controller equal to Meglonoth's power.
        this.addAbility(new BlocksTriggeredAbility(new MeglonothEffect(), false, true));

    }

    public Meglonoth(final Meglonoth card) {
        super(card);
    }

    @Override
    public Meglonoth copy() {
        return new Meglonoth(this);
    }
}

class MeglonothEffect extends OneShotEffect {

    public MeglonothEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to that creature's controller equal to {this}'s power";
    }

    public MeglonothEffect(final MeglonothEffect effect) {
        super(effect);
    }

    @Override
    public MeglonothEffect copy() {
        return new MeglonothEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent meglonoth = game.getPermanent(source.getSourceId());
        Permanent blocked = game.getPermanent(targetPointer.getFirst(game, source));
        if (blocked != null && meglonoth != null) {
            game.getPlayer(blocked.getControllerId()).damage(meglonoth.getPower().getValue(), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
