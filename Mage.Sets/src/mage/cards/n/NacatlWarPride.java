
package mage.cards.n;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public final class NacatlWarPride extends CardImpl {

    public NacatlWarPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Nacatl War-Pride must be blocked by exactly one creature if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneSourceEffect()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Whenever Nacatl War-Pride attacks, create X tokens that are copies of Nacatl War-Pride tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new NacatlWarPrideEffect(), false));
    }

    public NacatlWarPride(final NacatlWarPride card) {
        super(card);
    }

    @Override
    public NacatlWarPride copy() {
        return new NacatlWarPride(this);
    }
}

class NacatlWarPrideEffect extends OneShotEffect {

    public NacatlWarPrideEffect() {
        super(Outcome.Benefit);
        this.staticText = "create X tokens that are copies of Nacatl War-Pride tapped and attacking, where X is the number of creatures defending player controls. Exile the tokens at the beginning of the next end step.";
    }

    public NacatlWarPrideEffect(final NacatlWarPrideEffect effect) {
        super(effect);
    }

    @Override
    public NacatlWarPrideEffect copy() {
        return new NacatlWarPrideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent origNactalWarPride = game.getPermanent(source.getSourceId());
        if (origNactalWarPride == null) {
            return false;
        }

        // Count the number of creatures attacked opponent controls
        UUID defenderId = game.getCombat().getDefendingPlayerId(origNactalWarPride.getId(), game);
        int count = 0;
        if (defenderId != null) {
            count = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), defenderId, game);
        }

        if (count == 0) {
            return false;
        }

        List<Permanent> copies = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(origNactalWarPride);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), true, true);

            for (UUID tokenId : token.getLastAddedTokenIds()) { // by cards like Doubling Season multiple tokens can be added to the battlefield
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    copies.add(tokenPermanent);
                }
            }
        }

        if (!copies.isEmpty()) {
            FixedTargets fixedTargets = new FixedTargets(copies, game);
            ExileTargetEffect exileEffect = new ExileTargetEffect();
            exileEffect.setTargetPointer(fixedTargets);
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            return true;
        }

        return false;
    }
}
