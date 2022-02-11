
package mage.cards.d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class DualNature extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public DualNature(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // Whenever a nontoken creature enters the battlefield, its controller creates a token that's a copy of that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DualNatureCreateTokenEffect(), filter, false, SetTargetPointer.PERMANENT,
                "Whenever a nontoken creature enters the battlefield, its controller creates a token that's a copy of that creature."
        ));

        // Whenever a nontoken creature leaves the battlefield, exile all tokens with the same name as that creature.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new DualNatureCreatureLeavesEffect(), filter, false, SetTargetPointer.PERMANENT
        ));

        // When Dual Nature leaves the battlefield, exile all tokens created with Dual Nature.
        this.addAbility(new DualNatureLeavesBattlefieldTriggeredAbility());
    }

    private DualNature(final DualNature card) {
        super(card);
    }

    @Override
    public DualNature copy() {
        return new DualNature(this);
    }
}

class DualNatureCreateTokenEffect extends OneShotEffect {

    DualNatureCreateTokenEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "its controller creates a token that's a copy of that creature";
    }

    DualNatureCreateTokenEffect(final DualNatureCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public DualNatureCreateTokenEffect copy() {
        return new DualNatureCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(permanent.getControllerId());
            effect.setTargetPointer(targetPointer);
            effect.apply(game, source);
            Object object = game.getState().getValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game));
            Set<UUID> tokensCreated;
            if (object != null) {
                tokensCreated = (Set<UUID>) object;
            } else {
                tokensCreated = new HashSet<>();
            }
            for (Permanent perm : effect.getAddedPermanents()) {
                if (perm != null) {
                    tokensCreated.add(perm.getId());
                }
            }
            game.getState().setValue(CardUtil.getCardZoneString("_tokensCreated", source.getSourceId(), game), tokensCreated);
        }
        return true;
    }
}

class DualNatureCreatureLeavesEffect extends OneShotEffect {

    DualNatureCreatureLeavesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "exile all tokens with the same name as that creature";
    }

    DualNatureCreatureLeavesEffect(final DualNatureCreatureLeavesEffect effect) {
        super(effect);
    }

    @Override
    public DualNatureCreatureLeavesEffect copy() {
        return new DualNatureCreatureLeavesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(TokenPredicate.TRUE);
            filter.add(new NamePredicate(creature.getName()));
            new ExileAllEffect(filter).apply(game, source);
            return true;
        }
        return false;
    }
}

class DualNatureLeavesBattlefieldTriggeredAbility extends ZoneChangeTriggeredAbility {

    DualNatureLeavesBattlefieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, new DualNatureExileEffect(), "When {this} leaves the battlefield, ", false);
    }

    DualNatureLeavesBattlefieldTriggeredAbility(DualNatureLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof DualNatureExileEffect) {
                    ((DualNatureExileEffect) effect).setCardZoneString(CardUtil.getCardZoneString("_tokensCreated", this.getSourceId(), game, true));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DualNatureLeavesBattlefieldTriggeredAbility copy() {
        return new DualNatureLeavesBattlefieldTriggeredAbility(this);
    }
}

class DualNatureExileEffect extends OneShotEffect {

    private String cardZoneString;

    DualNatureExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all tokens created with {this}.";
    }

    DualNatureExileEffect(final DualNatureExileEffect effect) {
        super(effect);
        this.cardZoneString = effect.cardZoneString;
    }

    @Override
    public DualNatureExileEffect copy() {
        return new DualNatureExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = game.getState().getValue(cardZoneString);
        if (object != null) {
            Set<UUID> tokensCreated = (Set<UUID>) object;
            for (UUID tokenId : tokensCreated) {
                Permanent token = game.getPermanent(tokenId);
                if (token != null) {
                    token.destroy(source, game, true);
                }
            }
        }
        return true;
    }

    public void setCardZoneString(String cardZoneString) {
        this.cardZoneString = cardZoneString;
    }
}
