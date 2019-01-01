
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedByIdPredicate;
import mage.filter.predicate.permanent.BlockingAttackerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2 & L_J
 */
public final class SwornDefender extends CardImpl {

    public SwornDefender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by SwornDefender");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        // {1}: Sworn Defender’s power becomes the toughness of target creature blocking or being blocked by Sworn Defender minus 1 until end of turn, and Sworn Defender’s toughness becomes 1 plus the power of that creature until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SwornDefenderEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    public SwornDefender(final SwornDefender card) {
        super(card);
    }

    @Override
    public SwornDefender copy() {
        return new SwornDefender(this);
    }
}

class SwornDefenderEffect extends OneShotEffect {

    public SwornDefenderEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this}'s power becomes the toughness of target creature blocking or being blocked by {this} minus 1 until end of turn, and {this}’s toughness becomes 1 plus the power of that creature until end of turn";
    }

    public SwornDefenderEffect(final SwornDefenderEffect effect) {
        super(effect);
    }

    @Override
    public SwornDefenderEffect copy() {
        return new SwornDefenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (controller != null && targetPermanent != null) {
            int newPower = CardUtil.subtractWithOverflowCheck(targetPermanent.getToughness().getValue(), 1);
            int newToughness = CardUtil.addWithOverflowCheck(targetPermanent.getPower().getValue(), 1);
            game.addEffect(new SetPowerToughnessSourceEffect(newPower, newToughness, Duration.EndOfTurn, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
