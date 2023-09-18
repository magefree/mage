
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 *
 * @author North
 */
public final class WickedAkuba extends CardImpl {

    private static final FilterPlayer filter = new FilterPlayer("player dealt damage by Wicked Akuba this turn");

    static {
        filter.add(new WickedAkubaPredicate());
    }

    public WickedAkuba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}: Target player dealt damage by Wicked Akuba this turn loses 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ColoredManaCost(ColoredManaSymbol.B));
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
    }

    private WickedAkuba(final WickedAkuba card) {
        super(card);
    }

    @Override
    public WickedAkuba copy() {
        return new WickedAkuba(this);
    }
}

class WickedAkubaPredicate implements ObjectSourcePlayerPredicate<Player> {

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, input.getObject().getId());
        if (watcher != null) {
            return watcher.hasSourceDoneDamage(input.getSourceId(), game);
        }

        return false;
    }

    @Override
    public String toString() {
        return "(Player dealt damage by {this} this turn)";
    }
}
