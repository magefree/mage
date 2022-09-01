package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class SwornDefender extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature blocking or blocked by {this}");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.EITHER);
    }

    public SwornDefender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}: Sworn Defender’s power becomes the toughness of target creature blocking or being blocked by Sworn Defender minus 1 until end of turn, and Sworn Defender’s toughness becomes 1 plus the power of that creature until end of turn.
        Ability ability = new SimpleActivatedAbility(new SwornDefenderEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SwornDefender(final SwornDefender card) {
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
        this.staticText = "{this}'s power becomes the toughness of target creature blocking or being blocked by {this} minus 1 until end of turn, and {this}'s toughness becomes 1 plus the power of that creature until end of turn";
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
        Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && targetPermanent != null) {
            int newPower = CardUtil.overflowDec(targetPermanent.getToughness().getValue(), 1);
            int newToughness = CardUtil.overflowInc(targetPermanent.getPower().getValue(), 1);
            game.addEffect(new SetBasePowerToughnessSourceEffect(newPower, newToughness, Duration.EndOfTurn, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
