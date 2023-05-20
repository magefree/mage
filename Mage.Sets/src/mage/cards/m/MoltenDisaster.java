package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MoltenDisaster extends CardImpl {

    public MoltenDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");


        // If Molten Disaster was kicked, it has split second.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new MoltenDisasterSplitSecondEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Kicker {R}
        this.addAbility(new KickerAbility("{R}"));
        // Molten Disaster deals X damage to each creature without flying and each player.
        this.getSpellAbility().addEffect(new MoltenDisasterEffect());
    }

    private MoltenDisaster(final MoltenDisaster card) {
        super(card);
    }

    @Override
    public MoltenDisaster copy() {
        return new MoltenDisaster(this);
    }
}

class MoltenDisasterSplitSecondEffect extends ContinuousRuleModifyingEffectImpl {

    MoltenDisasterSplitSecondEffect() {
        super(Duration.WhileOnStack, Outcome.Detriment);
        staticText = "if this spell was kicked, it has split second. <i>(As long as this spell is on the stack, players can't cast spells or activate abilities that aren't mana abilities.)</i>";
    }

    MoltenDisasterSplitSecondEffect(final MoltenDisasterSplitSecondEffect effect) {
        super(effect);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "You can't cast spells or activate abilities that aren't mana abilities (Split second).";
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            if (KickedCondition.ONCE.apply(game, source)) {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
            if (ability.isPresent() && !(ability.get() instanceof ActivatedManaAbilityImpl)) {
                return KickedCondition.ONCE.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public MoltenDisasterSplitSecondEffect copy() {
        return new MoltenDisasterSplitSecondEffect(this);
    }
}

class MoltenDisasterEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public MoltenDisasterEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to each creature without flying and each player";
    }

    public MoltenDisasterEffect(final MoltenDisasterEffect effect) {
        super(effect);
    }

    @Override
    public MoltenDisasterEffect copy() {
        return new MoltenDisasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            permanent.damage(amount, source.getSourceId(), source, game, false, true);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(amount, source.getSourceId(), source, game);
            }
        }
        return true;
    }

}
