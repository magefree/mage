

package mage.game.permanent.token;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author spjspj
 */
public final class SengirNosferatuBatToken extends TokenImpl {

    public SengirNosferatuBatToken() {
        super("Bat", "1/2 black Bat creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BAT);
        power = new MageInt(1);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
        ReturnSengirNosferatuEffect effect = new ReturnSengirNosferatuEffect();
        effect.setText("Return an exiled card named Sengir Nosferatu to the battlefield under its owner's control.");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public SengirNosferatuBatToken(final SengirNosferatuBatToken token) {
        super(token);
    }

    public SengirNosferatuBatToken copy() {
        return new SengirNosferatuBatToken(this);
    }
}

class ReturnSengirNosferatuEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("exiled card named Sengir Nosferatu");

    static {
        filter.add(new NamePredicate("Sengir Nosferatu"));
    }

    public ReturnSengirNosferatuEffect() {
        super(Outcome.Benefit);
    }

    public ReturnSengirNosferatuEffect(final ReturnSengirNosferatuEffect effect) {
        super(effect);
    }

    @Override
    public ReturnSengirNosferatuEffect copy() {
        return new ReturnSengirNosferatuEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controllerId = source.getControllerId();
        Target target = new TargetCardInExile(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getSourceId(), controllerId, game)) {
            return false;
        }
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.chooseTarget(Outcome.PutCreatureInPlay, target, source, game);
            Card card = game.getCard(target.getTargets().get(0));
            if (card != null) {
                return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
            }
        }
        return false;
    }
}
