package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.command.emblems.TeferiWhoSlowsTheSunsetEmblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.ArrayList;
import java.util.List;
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
        this.addAbility(new LoyaltyAbility(new TeferiWhoSlowsTheSunsetEffect(), 1));

        // −2: Look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(StaticValue.get(3), false, StaticValue.get(1), new FilterCard("card"), false, false), -2));

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
        staticText = "Choose up to one target artifact, up to one target creature, and up to one target land." +
                "Untap the chosen permanents you control." +
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
        List<Permanent> chosen = new ArrayList<>();
        Player player = game.getPlayer(source.getControllerId());

        Target targetArtifact = new TargetPermanent(1, 1, new FilterArtifactPermanent(), true);
        if (targetArtifact.canChoose(source.getSourceId(), player.getId(), game)) {
            while (player.canRespond() && !targetArtifact.isChosen() && targetArtifact.canChoose(source.getSourceId(), player.getId(), game)) {
                player.chooseTarget(Outcome.Benefit, targetArtifact, source, game);
            }
            Permanent artifact = game.getPermanent(targetArtifact.getFirstTarget());
            if (artifact != null) {
                chosen.add(artifact);
            }
            targetArtifact.clearChosen();
        }

        Target targetCreature = new TargetPermanent(1, 1, new FilterCreaturePermanent(), true);
        if (targetCreature.canChoose(source.getSourceId(), player.getId(), game)) {
            while (player.canRespond() && !targetCreature.isChosen() && targetCreature.canChoose(source.getSourceId(), player.getId(), game)) {
                player.chooseTarget(Outcome.Benefit, targetCreature, source, game);
            }
            Permanent creature = game.getPermanent(targetCreature.getFirstTarget());
            if (creature != null) {
                chosen.add(creature);
            }
            targetCreature.clearChosen();
        }

        Target targetLand = new TargetPermanent(1, 1, new FilterLandPermanent(), true);
        if (targetLand.canChoose(source.getSourceId(), player.getId(), game)) {
            while (player.canRespond() && !targetLand.isChosen() && targetLand.canChoose(source.getSourceId(), player.getId(), game)) {
                player.chooseTarget(Outcome.Benefit, targetLand, source, game);
            }
            Permanent land = game.getPermanent(targetLand.getFirstTarget());
            if (land != null) {
                chosen.add(land);
            }
            targetLand.clearChosen();
        }

        for (Permanent permanent : chosen) {
            if (permanent != null) {
                if (permanent.getControllerId() == player.getId()) {
                    permanent.untap(game);
                } else {
                    permanent.tap(source, game);
                }
            }
        }

        player.gainLife(2, game, source);
        return true;
    }
}
