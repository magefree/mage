package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KatabaticWinds extends CardImpl {

    public KatabaticWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // Creatures with flying can't attack or block, and their activated abilities with {tap} in their costs can't be activated.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new KatabaticWindsRestrictionEffect());
        ability.addEffect(new KatabaticWindsRuleModifyingEffect());
        this.addAbility(ability);

    }

    private KatabaticWinds(final KatabaticWinds card) {
        super(card);
    }

    @Override
    public KatabaticWinds copy() {
        return new KatabaticWinds(this);
    }
}

class KatabaticWindsRestrictionEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public KatabaticWindsRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with flying can't attack or block";
    }

    public KatabaticWindsRestrictionEffect(final KatabaticWindsRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public KatabaticWindsRestrictionEffect copy() {
        return new KatabaticWindsRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}

class KatabaticWindsRuleModifyingEffect extends ContinuousRuleModifyingEffectImpl {

    public KatabaticWindsRuleModifyingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = ", and their activated abilities with {T} in their costs can't be activated";
    }

    public KatabaticWindsRuleModifyingEffect(final KatabaticWindsRuleModifyingEffect effect) {
        super(effect);
    }

    @Override
    public KatabaticWindsRuleModifyingEffect copy() {
        return new KatabaticWindsRuleModifyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        return ability.isPresent()
                && object != null
                && object.isCreature(game)
                && object.getAbilities().contains(FlyingAbility.getInstance())
                && game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId())
                && ability.get().hasTapCost();
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        return "Creatures with flying can't use their activated abilities that use {tap} in their costs.";
    }
}
