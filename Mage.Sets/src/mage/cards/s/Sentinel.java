
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
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
 * @author LevelX2
 */
public final class Sentinel extends CardImpl {

    public Sentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature blocking or blocked by Sentinel");
        filter.add(Predicates.or(new BlockedByIdPredicate(this.getId()),
                new BlockingAttackerIdPredicate(this.getId())));
        // 0: Change Sentinel's base toughness to 1 plus the power of target creature blocking or blocked by Sentinel. (This effect lasts indefinitely.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SentinelEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private Sentinel(final Sentinel card) {
        super(card);
    }

    @Override
    public Sentinel copy() {
        return new Sentinel(this);
    }
}

class SentinelEffect extends OneShotEffect {

    public SentinelEffect() {
        super(Outcome.Detriment);
        this.staticText = "Change {this}'s base toughness to 1 plus the power of target creature blocking or blocked by {this}. <i>(This effect lasts indefinitely.)</i>";
    }

    public SentinelEffect(final SentinelEffect effect) {
        super(effect);
    }

    @Override
    public SentinelEffect copy() {
        return new SentinelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (controller != null && targetPermanent != null) {
            int newToughness = CardUtil.overflowInc(targetPermanent.getPower().getValue(), 1);
            game.addEffect(new SetToughnessSourceEffect(StaticValue.get(newToughness), Duration.Custom, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}
