package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterOpponent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class KeeperOfTheFlame extends CardImpl {
    
    private static final FilterOpponent filter = new FilterOpponent();
    
    static {
        filter.add(new KeeperOfTheFlamePredicate());
    }
    
    public KeeperOfTheFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {tap}: Choose target opponent who had more life than you did as you activated this ability. Keeper of the Flame deals 2 damage to that player.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2).setText("Choose target opponent who had more life than you did as you activated this ability. {this} deals 2 damage to that player"),
                new ManaCostsImpl<>("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer(1, 1, false, filter));
        this.addAbility(ability);
        
    }
    
    private KeeperOfTheFlame(final KeeperOfTheFlame card) {
        super(card);
    }
    
    @Override
    public KeeperOfTheFlame copy() {
        return new KeeperOfTheFlame(this);
    }
}

class KeeperOfTheFlamePredicate implements ObjectSourcePlayerPredicate<Player> {
    
    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetOpponent = input.getObject();
        Player controller = game.getPlayer(input.getPlayerId());
        if (targetOpponent == null
                || controller == null
                || !controller.hasOpponent(targetOpponent.getId(), game)) {
            return false;
        }
        return targetOpponent.getLife() > controller.getLife();
    }
    
    @Override
    public String toString() {
        return "opponent who had more life than you did as you activated this ability";
    }
}
