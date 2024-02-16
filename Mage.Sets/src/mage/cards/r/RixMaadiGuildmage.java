
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterBlockingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX2
 */
public final class RixMaadiGuildmage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterBlockingCreature("blocking creature");
    private static final FilterPlayer playerFilter = new FilterPlayer("player who lost life this turn");
    static {
        playerFilter.add(new PlayerLostLifePredicate());
    }

    public RixMaadiGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}{R}: Target blocking creature gets -1/-1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1, -1, Duration.EndOfTurn),new ManaCostsImpl<>("{B}{R}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {B}{R}: Target player who lost life this turn loses 1 life.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{B}{R}"));
        ability.addTarget(new TargetPlayer(1,1,false, playerFilter));
        this.addAbility(ability);
    }

    private RixMaadiGuildmage(final RixMaadiGuildmage card) {
        super(card);
    }

    @Override
    public RixMaadiGuildmage copy() {
        return new RixMaadiGuildmage(this);
    }
}

class PlayerLostLifePredicate implements Predicate<Player> {

    public PlayerLostLifePredicate() {

    }

    @Override
    public boolean apply(Player input, Game game) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return (0 < watcher.getLifeLost(input.getId()));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player lost life";
    }
}