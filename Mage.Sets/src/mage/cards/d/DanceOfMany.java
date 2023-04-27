package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DanceOfMany extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public DanceOfMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // When Dance of Many enters the battlefield, create a token that's a copy of target nontoken creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DanceOfManyCreateTokenCopyEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // When Dance of Many leaves the battlefield, exile the token.
        // When the token leaves the battlefield, sacrifice Dance of Many.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new DanceOfManyExileTokenEffect(), false);
        ability2.addEffect(new InfoEffect("When the token leaves the battlefield, sacrifice {this}"));
        this.addAbility(ability2);

        // At the beginning of your upkeep, sacrifice Dance of Many unless you pay {U}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{U}{U}")), TargetController.YOU, false));

    }

    private DanceOfMany(final DanceOfMany card) {
        super(card);
    }

    @Override
    public DanceOfMany copy() {
        return new DanceOfMany(this);
    }
}

class DanceOfManyCreateTokenCopyEffect extends OneShotEffect {

    public DanceOfManyCreateTokenCopyEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a token that's a copy of target nontoken creature";
    }

    public DanceOfManyCreateTokenCopyEffect(final DanceOfManyCreateTokenCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // The ability is targeted and checks the validity of the target when put on the stack and when resolving.
        // If the creature is not still there when the copy ability resolves, the ability doesn’t resolve and no token
        // is put onto the battlefield. This card remains on the battlefield as an enchantment with no token.
        // (2004-10-04)
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (permanent != null && sourceObject != null) {

            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
            game.getState().setValue(source.getSourceId() + "_token", effect.getAddedPermanents());
            for (Permanent addedToken : effect.getAddedPermanents()) {
                Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice Dance of Many");
                sacrificeEffect.setTargetPointer(new FixedTarget(sourceObject, game));
                LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(sacrificeEffect, false);
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(triggerAbility, Duration.WhileOnBattlefield);
                continuousEffect.setTargetPointer(new FixedTarget(addedToken, game));
                game.addEffect(continuousEffect, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public DanceOfManyCreateTokenCopyEffect copy() {
        return new DanceOfManyCreateTokenCopyEffect(this);
    }
}

class DanceOfManyExileTokenEffect extends OneShotEffect {

    public DanceOfManyExileTokenEffect() {
        super(Outcome.Removal);
        staticText = "exile the token";
    }

    public DanceOfManyExileTokenEffect(final DanceOfManyExileTokenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> tokenPermanents = (List<Permanent>) game.getState().getValue(source.getSourceId() + "_token");
            if (tokenPermanents != null) {
                Cards cards = new CardsImpl();
                for (Permanent permanent : tokenPermanents) {
                    cards.add(permanent);
                }
                controller.moveCards(cards, Zone.EXILED, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public DanceOfManyExileTokenEffect copy() {
        return new DanceOfManyExileTokenEffect(this);
    }
}
