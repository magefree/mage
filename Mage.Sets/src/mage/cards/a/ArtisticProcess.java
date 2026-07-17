package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.Elemental33BlueRedToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtisticProcess extends CardImpl {

    public ArtisticProcess(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Choose one --
        // * Artistic Process deals 6 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Artistic Process deals 2 damage to each creature you don't control.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(2, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL)));

        // * Create a 3/3 blue and red Elemental creature token with flying. It gains haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new ArtisticProcessEffect()));
    }

    private ArtisticProcess(final ArtisticProcess card) {
        super(card);
    }

    @Override
    public ArtisticProcess copy() {
        return new ArtisticProcess(this);
    }
}

class ArtisticProcessEffect extends OneShotEffect {

    ArtisticProcessEffect() {
        super(Outcome.Benefit);
        staticText = "create a 3/3 blue and red Elemental creature token with flying. It gains haste until end of turn";
    }

    private ArtisticProcessEffect(final ArtisticProcessEffect effect) {
        super(effect);
    }

    @Override
    public ArtisticProcessEffect copy() {
        return new ArtisticProcessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new Elemental33BlueRedToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
