package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class MakeshiftMannequin extends CardImpl {

    public MakeshiftMannequin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Return target creature card from your graveyard to the battlefield 
        // with a mannequin counter on it. For as long as that creature has a 
        // mannequin counter on it, it has "When this creature becomes the target 
        // of a spell or ability, sacrifice it."
        this.getSpellAbility().addEffect(new MakeshiftMannequinEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private MakeshiftMannequin(final MakeshiftMannequin card) {
        super(card);
    }

    @Override
    public MakeshiftMannequin copy() {
        return new MakeshiftMannequin(this);
    }
}

class MakeshiftMannequinEffect extends OneShotEffect {

    MakeshiftMannequinEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard "
                + "to the battlefield with a mannequin counter on it. "
                + "For as long as that creature has a mannequin counter on it, "
                + "it has \"When this creature becomes the target of a spell "
                + "or ability, sacrifice it.\"";
    }

    MakeshiftMannequinEffect(final MakeshiftMannequinEffect effect) {
        super(effect);
    }

    @Override
    public MakeshiftMannequinEffect copy() {
        return new MakeshiftMannequinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID cardId = this.getTargetPointer().getFirst(game, source);
            Card card = controller.getGraveyard().get(cardId, game);
            if (card != null) {
                Counters counters = new Counters();
                counters.addCounter(CounterType.MANNEQUIN.createInstance());
                game.setEnterWithCounters(cardId, counters);
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(cardId);
                    if (permanent != null) {
                        ContinuousEffect gainedEffect = new MakeshiftMannequinGainAbilityEffect();
                        // Bug #6885 Fixed when owner/controller leaves the game the effect still applies
                        SimpleStaticAbility gainAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, gainedEffect);
                        gainAbility.setSourceId(cardId);
                        gainAbility.getTargets().add(source.getTargets().get(0));
                        game.addEffect(gainedEffect, gainAbility);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class MakeshiftMannequinGainAbilityEffect extends ContinuousEffectImpl {

    MakeshiftMannequinGainAbilityEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    MakeshiftMannequinGainAbilityEffect(final MakeshiftMannequinGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.addAbility(
                    new SourceBecomesTargetTriggeredAbility(
                            new SacrificeSourceEffect()),
                    source.getSourceId(), game);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        return permanent == null
                || permanent.getCounters(game).getCount(CounterType.MANNEQUIN) < 1;
    }

    @Override
    public MakeshiftMannequinGainAbilityEffect copy() {
        return new MakeshiftMannequinGainAbilityEffect(this);
    }
}
