
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.command.emblems.KioraEmblem;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class KioraTheCrashingWave extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KioraTheCrashingWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KIORA);

        this.setStartingLoyalty(2);

        // +1: Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.
        LoyaltyAbility ability = new LoyaltyAbility(new KioraPreventionEffect(), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -1: Draw a card. You may play an additional land this turn.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), -1);
        ability.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.addAbility(ability);

        // -5: You get an emblem with "At the beginning of your end step, create a 9/9 blue Kraken creature token."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new KioraEmblem()), -5));

    }

    private KioraTheCrashingWave(final KioraTheCrashingWave card) {
        super(card);
    }

    @Override
    public KioraTheCrashingWave copy() {
        return new KioraTheCrashingWave(this);
    }
}

class KioraPreventionEffect extends PreventionEffectImpl {

    public KioraPreventionEffect() {
        super(Duration.UntilYourNextTurn, Integer.MAX_VALUE, false, false);
        staticText = "Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls";
    }

    public KioraPreventionEffect(final KioraPreventionEffect effect) {
        super(effect);
    }

    @Override
    public KioraPreventionEffect copy() {
        return new KioraPreventionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                permanent.addInfo("kioraPrevention" + getId(), CardUtil.addToolTipMarkTags("All damage that would be dealt to and dealt by this permanent is prevented."), game);
            }
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            Permanent targetPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (targetPermanent != null
                    && (event.getSourceId().equals(targetPermanent.getId()) || event.getTargetId().equals(targetPermanent.getId()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (super.isInactive(source, game)) {
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.addInfo("kioraPrevention" + getId(), "", game);
                }
            }
            return true;
        }
        return false;
    }
}
