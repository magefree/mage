
package mage.cards.g;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class GreatOakGuardian extends CardImpl {

    public GreatOakGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Great Oak Guardian enters the battlefield, creatures target player controls get +2/+2 until end of turn. Untap them.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GreatOakGuardianEffect(), false);
        ability.addEffect(new GreatOakGuardianUntapEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GreatOakGuardian(final GreatOakGuardian card) {
        super(card);
    }

    @Override
    public GreatOakGuardian copy() {
        return new GreatOakGuardian(this);
    }
}

class GreatOakGuardianEffect extends ContinuousEffectImpl {

    public GreatOakGuardianEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "creatures target player controls get +2/+2 until end of turn";
    }

    public GreatOakGuardianEffect(final GreatOakGuardianEffect effect) {
        super(effect);
    }

    @Override
    public GreatOakGuardianEffect copy() {
        return new GreatOakGuardianEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(2);
                permanent.addToughness(2);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

class GreatOakGuardianUntapEffect extends OneShotEffect {

    public GreatOakGuardianUntapEffect() {
        super(Outcome.Benefit);
        this.staticText = "untap them";
    }

    public GreatOakGuardianUntapEffect(final GreatOakGuardianUntapEffect effect) {
        super(effect);
    }

    @Override
    public GreatOakGuardianUntapEffect copy() {
        return new GreatOakGuardianUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }
}
