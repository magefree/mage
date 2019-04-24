
package mage.cards.r;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.command.emblems.RowanKenrithEmblem;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class RowanKenrith extends CardImpl {

    public RowanKenrith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROWAN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +2: During target player's next turn, each creature that player controls attacks if able.
        LoyaltyAbility ability = new LoyaltyAbility(new RowanKenrithAttackEffect(), 2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -2: Rowan Kenrith deals 3 damage to each tapped creature target player controls.
        ability = new LoyaltyAbility(new RowanKenrithDamageEffect(), -2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // -8: Target player gets an emblem with "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy."
        Effect effect = new GetEmblemTargetPlayerEffect(new RowanKenrithEmblem());
        effect.setText(
                "Target player gets an emblem with "
                + "\"Whenever you activate an ability that isn't a mana ability, "
                + "copy it. You may choose new targets for the copy.\""
        );
        ability = new LoyaltyAbility(effect, -8);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Partner with Will Kenrith
        this.addAbility(new PartnerWithAbility("Will Kenrith", true, false));

        // Rowan Kenrith can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public RowanKenrith(final RowanKenrith card) {
        super(card);
    }

    @Override
    public RowanKenrith copy() {
        return new RowanKenrith(this);
    }
}

class RowanKenrithAttackEffect extends RequirementEffect {

    protected MageObjectReference creatingPermanent;

    public RowanKenrithAttackEffect() {
        super(Duration.Custom);
        staticText = "During target player's next turn, creatures that player controls attack if able";
    }

    public RowanKenrithAttackEffect(final RowanKenrithAttackEffect effect) {
        super(effect);
        this.creatingPermanent = effect.creatingPermanent;
    }

    @Override
    public RowanKenrithAttackEffect copy() {
        return new RowanKenrithAttackEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        creatingPermanent = new MageObjectReference(source.getSourceId(), game);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(source.getFirstTarget());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return (startingTurn != game.getTurnNum()
                && (game.getPhase().getType() == TurnPhase.END
                && game.isActivePlayer(source.getFirstTarget())))
                || // 6/15/2010: If a creature controlled by the affected player can't attack Gideon Jura (because he's no longer on the battlefield, for example), that player may have it attack you, another one of your planeswalkers, or nothing at all.
                creatingPermanent.getPermanent(game) == null;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}

class RowanKenrithDamageEffect extends OneShotEffect {

    RowanKenrithDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals 3 damage to each tapped creature target player controls";
    }

    RowanKenrithDamageEffect(final RowanKenrithDamageEffect effect) {
        super(effect);
    }

    @Override
    public RowanKenrithDamageEffect copy() {
        return new RowanKenrithDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new TappedPredicate());
        filter.add(new ControllerIdPredicate(source.getFirstTarget()));
        return new DamageAllEffect(3, filter).apply(game, source);
    }
}
