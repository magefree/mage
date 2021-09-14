package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WaxingMoon extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WEREWOLF);

    public WaxingMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Transform up to one target Werewolf you control.
        this.getSpellAbility().addEffect(new WaxingMoonEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));

        // Creatures you control gain trample until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private WaxingMoon(final WaxingMoon card) {
        super(card);
    }

    @Override
    public WaxingMoon copy() {
        return new WaxingMoon(this);
    }
}

class WaxingMoonEffect extends OneShotEffect {

    WaxingMoonEffect() {
        super(Outcome.Benefit);
        staticText = "transform up to one target Werewolf you control";
    }

    private WaxingMoonEffect(final WaxingMoonEffect effect) {
        super(effect);
    }

    @Override
    public WaxingMoonEffect copy() {
        return new WaxingMoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        return permanent != null && permanent.transform(source, game);
    }
}
