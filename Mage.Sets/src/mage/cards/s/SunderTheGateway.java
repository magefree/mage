package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunderTheGateway extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactOrEnchantmentPermanent("nontoken artifact or enchantment an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public SunderTheGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Choose one --
        // * Destroy target nontoken artifact or enchantment an opponent controls. Incubate 2.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new IncubateEffect(2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Incubate 2, then transform an Incubator token you control.
        this.getSpellAbility().addMode(new Mode(new IncubateEffect(2)).addEffect(new SunderTheGatewayEffect()));
    }

    private SunderTheGateway(final SunderTheGateway card) {
        super(card);
    }

    @Override
    public SunderTheGateway copy() {
        return new SunderTheGateway(this);
    }
}

class SunderTheGatewayEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.INCUBATOR, "Incubator token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    SunderTheGatewayEffect() {
        super(Outcome.Benefit);
        staticText = ", then transform an Incubator token you control";
    }

    private SunderTheGatewayEffect(final SunderTheGatewayEffect effect) {
        super(effect);
    }

    @Override
    public SunderTheGatewayEffect copy() {
        return new SunderTheGatewayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.transform(source, game);
    }
}
