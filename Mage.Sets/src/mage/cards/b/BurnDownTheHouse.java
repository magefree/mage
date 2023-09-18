package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.token.DevilToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BurnDownTheHouse extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker");

    public BurnDownTheHouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Choose one —
        // • Burn Down the House deals 5 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DamageAllEffect(5, filter));

        // • Create three 1/1 red Devil creature tokens with "When this creature dies, it deals 1 damage to any target." They gain haste until end of turn.
        this.getSpellAbility().addMode(new Mode(new BurnDownTheHouseEffect()));
    }

    private BurnDownTheHouse(final BurnDownTheHouse card) {
        super(card);
    }

    @Override
    public BurnDownTheHouse copy() {
        return new BurnDownTheHouse(this);
    }
}

class BurnDownTheHouseEffect extends OneShotEffect {

    BurnDownTheHouseEffect() {
        super(Outcome.Benefit);
        staticText = "create three 1/1 red Devil creature tokens with " +
                "\"When this creature dies, it deals 1 damage to any target.\" They gain haste until end of turn";
    }

    private BurnDownTheHouseEffect(final BurnDownTheHouseEffect effect) {
        super(effect);
    }

    @Override
    public BurnDownTheHouseEffect copy() {
        return new BurnDownTheHouseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new DevilToken();
        token.putOntoBattlefield(3, game, source, source.getControllerId());
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
// watch out
// you might get what you're after
