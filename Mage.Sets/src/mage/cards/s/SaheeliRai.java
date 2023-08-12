package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardWithDifferentNameInLibrary;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SaheeliRai extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("artifact cards with different names");

    public SaheeliRai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAHEELI);

        this.setStartingLoyalty(3);

        // +1: Scry 1. Saheeli Rai deals 1 damage to each opponent.
        Effect effect = new ScryEffect(1);
        effect.setText("Scry 1");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.addAbility(ability);

        // -2: Create a token that's a copy of target artifact or creature you control, except it's an artifact in addition to its other types. That token gains haste. Exile it at the beginning of the next end step.
        ability = new LoyaltyAbility(new SaheeliRaiCreateTokenEffect(), -2);
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // -7: Search your library for up to three artifact cards with different names, put them onto the battlefield, then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardWithDifferentNameInLibrary(0, 3, filter)), -7));
    }

    private SaheeliRai(final SaheeliRai card) {
        super(card);
    }

    @Override
    public SaheeliRai copy() {
        return new SaheeliRai(this);
    }
}

class SaheeliRaiCreateTokenEffect extends OneShotEffect {

    SaheeliRaiCreateTokenEffect() {
        super(Outcome.Copy);
        this.staticText = "Create a token that's a copy of target artifact or creature you control, except it's an artifact in addition to its other types. That token gains haste. Exile it at the beginning of the next end step";
    }

    SaheeliRaiCreateTokenEffect(final SaheeliRaiCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliRaiCreateTokenEffect copy() {
        return new SaheeliRaiCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent copiedPermanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (copiedPermanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(null, CardType.ARTIFACT, true);
            if (effect.apply(game, source)) {
                for (Permanent copyPermanent : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(copyPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
                return true;
            }
        }
        return false;
    }
}
