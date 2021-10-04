package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TeferiWhoSlowsTheSunsetEmblem;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class TeferiWhoSlowsTheSunset extends CardImpl {

    public TeferiWhoSlowsTheSunset(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEFERI);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Choose up to one target artifact, up to one target creature, and up to one target land. Untap the chosen permanents you control. Tap the chosen permanents you don't control. You gain 2 life.
        Ability ability = new LoyaltyAbility(new TeferiWhoSlowsTheSunsetEffect(), 1);
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetArtifactPermanent());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // −2: Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, false, false
        ), -2));

        // −7: You get an emblem with "Untap all permanents you control during each opponent's untap step" and "You draw a card during each opponent's draw step."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new TeferiWhoSlowsTheSunsetEmblem()), -7));
    }

    private TeferiWhoSlowsTheSunset(final TeferiWhoSlowsTheSunset card) {
        super(card);
    }

    @Override
    public TeferiWhoSlowsTheSunset copy() {
        return new TeferiWhoSlowsTheSunset(this);
    }
}

class TeferiWhoSlowsTheSunsetEffect extends OneShotEffect {

    TeferiWhoSlowsTheSunsetEffect() {
        super(Outcome.Benefit);
        staticText = "Choose up to one target artifact, up to one target creature, and up to one target land. " +
                "Untap the chosen permanents you control. " +
                "Tap the chosen permanents you don't control.";
    }

    private TeferiWhoSlowsTheSunsetEffect(final TeferiWhoSlowsTheSunsetEffect effect) {
        super(effect);
    }

    @Override
    public TeferiWhoSlowsTheSunsetEffect copy() {
        return new TeferiWhoSlowsTheSunsetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            Permanent targetPermanent = game.getPermanent(target.getFirstTarget());
            if (targetPermanent == null) {
                continue;
            }
            if (targetPermanent.isControlledBy(source.getControllerId())) {
                targetPermanent.untap(game);
            } else {
                targetPermanent.tap(source, game);
            }
        }
        return true;
    }
}
