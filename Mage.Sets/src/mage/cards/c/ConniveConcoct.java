package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class ConniveConcoct extends SplitCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ConniveConcoct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U/B}{U/B}", "{3}{U}{B}", SpellAbilityType.SPLIT);

        // Connive
        // Gain control of target creature with power 2 or less.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new GainControlTargetEffect(Duration.Custom)
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetCreaturePermanent(filter)
        );

        // Concoct
        // Surveil 3, then return a creature card from your graveyard to the battlefield.
        this.getRightHalfCard().getSpellAbility().addEffect(new ConcoctEffect());
    }

    private ConniveConcoct(final ConniveConcoct card) {
        super(card);
    }

    @Override
    public ConniveConcoct copy() {
        return new ConniveConcoct(this);
    }
}

class ConcoctEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card in your graveyard");

    public ConcoctEffect() {
        super(Outcome.Benefit);
        this.staticText = "Surveil 3, then return a creature card "
                + "from your graveyard to the battlefield.";
    }

    public ConcoctEffect(final ConcoctEffect effect) {
        super(effect);
    }

    @Override
    public ConcoctEffect copy() {
        return new ConcoctEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.surveil(3, source, game);
        Target target = new TargetCardInYourGraveyard(filter);
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
            effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            effect.apply(game, source);
        }
        return true;
    }
}
