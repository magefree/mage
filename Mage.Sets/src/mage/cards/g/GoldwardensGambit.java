package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RebelRedToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldwardensGambit extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.EQUIPMENT, "Equipment");

    private static final Hint hint = new ValueHint(
            "Equipment you control", new PermanentsOnBattlefieldCount(filter)
    );

    public GoldwardensGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}{R}");

        // Affinity for Equipment
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Create five 2/2 red Rebel creature tokens. They gain haste until end of turn. For each of those tokens, you may attach an Equipment you control to it.
        this.getSpellAbility().addEffect(new GoldwardensGambitEffect());
    }

    private GoldwardensGambit(final GoldwardensGambit card) {
        super(card);
    }

    @Override
    public GoldwardensGambit copy() {
        return new GoldwardensGambit(this);
    }

    public static FilterControlledPermanent getFilter() {
        return filter;
    }
}

class GoldwardensGambitEffect extends OneShotEffect {

    GoldwardensGambitEffect() {
        super(Outcome.Benefit);
        staticText = "create five 2/2 red Rebel creature tokens. They gain haste until end of turn. " +
                "For each of those tokens, you may attach an Equipment you control to it";
    }

    private GoldwardensGambitEffect(final GoldwardensGambitEffect effect) {
        super(effect);
    }

    @Override
    public GoldwardensGambitEffect copy() {
        return new GoldwardensGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Token token = new RebelRedToken();
        token.putOntoBattlefield(5, game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        if (game.getBattlefield().count(GoldwardensGambit.getFilter(), source.getControllerId(), source, game) < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null || !player.chooseUse(
                    Outcome.BoostCreature, "Attach an equipment you control to " +
                            permanent.getIdName() + '?', source, game
            )) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 1, GoldwardensGambit.getFilter(), true);
            player.choose(Outcome.BoostCreature, target, source, game);
            permanent.addAttachment(target.getFirstTarget(), source, game);
        }
        return true;
    }
}
