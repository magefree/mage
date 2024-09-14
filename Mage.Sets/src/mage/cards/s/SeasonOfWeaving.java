package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class SeasonOfWeaving extends CardImpl {
    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public SeasonOfWeaving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Choose up to five {P} worth of modes. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMaxPawPrints(5);
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(5);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // {P} -- Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().getMode().withPawPrintValue(1);

        // {P}{P} -- Choose an artifact or creature you control. Create a token that's a copy of it.
        Mode mode2 = new Mode(new SeasonOfWeavingEffect());
        this.getSpellAbility().addMode(mode2.withPawPrintValue(2));

        // {P}{P}{P} -- Return each nonland, nontoken permanent to its owner's hand.
        Mode mode3 = new Mode(new ReturnToHandFromBattlefieldAllEffect(filter));
        this.getSpellAbility().addMode(mode3.withPawPrintValue(3));
    }

    private SeasonOfWeaving(final SeasonOfWeaving card) {
        super(card);
    }

    @Override
    public SeasonOfWeaving copy() {
        return new SeasonOfWeaving(this);
    }
}


class SeasonOfWeavingEffect extends OneShotEffect {

    SeasonOfWeavingEffect() {
        super(Outcome.PutCreatureInPlay);
        setText("Choose an artifact or creature you control. Create a token that's a copy of it");
    }

    private SeasonOfWeavingEffect(final SeasonOfWeavingEffect effect) {
        super(effect);
    }

    @Override
    public SeasonOfWeavingEffect copy() {
        return new SeasonOfWeavingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE).withNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Effect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            effect.apply(game, source);
        }
        return true;
    }
}
