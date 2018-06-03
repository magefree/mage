
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public final class SeverSoul extends CardImpl {

    private static final FilterCreaturePermanent nonBlackCreature = new FilterCreaturePermanent("nonblack creature");

    static {
        nonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public SeverSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(nonBlackCreature));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        // You gain life equal to its toughness.
        this.getSpellAbility().addEffect(new GainLifeEqualToToughnessEffect());
    }

    public SeverSoul(final SeverSoul card) {
        super(card);
    }

    @Override
    public SeverSoul copy() {
        return new SeverSoul(this);
    }
}

class GainLifeEqualToToughnessEffect extends OneShotEffect {

    public GainLifeEqualToToughnessEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to its toughness.";
    }

    public GainLifeEqualToToughnessEffect(final GainLifeEqualToToughnessEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(permanent.getToughness().getValue(), game, source);
            }
        }
        return false;
    }

    @Override
    public GainLifeEqualToToughnessEffect copy() {
        return new GainLifeEqualToToughnessEffect(this);
    }
}
