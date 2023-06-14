package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GideonBattleForged extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public GideonBattleForged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.color.setWhite(true);

        this.nightCard = true;

        this.setStartingLoyalty(3);

        // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new GideonBattleForgedAttacksIfAbleTargetEffect(Duration.Custom), 2);
        loyaltyAbility.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(loyaltyAbility);

        // +1: Until your next turn, target creature gains indestructible. Untap that creature.
        Effect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn);
        effect.setText("Until your next turn, target creature gains indestructible");
        loyaltyAbility = new LoyaltyAbility(effect, 1);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        effect = new UntapTargetEffect();
        effect.setText("Untap that creature");
        loyaltyAbility.addEffect(effect);
        this.addAbility(loyaltyAbility);

        // 0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonBattleForgedToken(), CardType.PLANESWALKER, Duration.EndOfTurn), 0);
        effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability3.addEffect(effect);
        this.addAbility(ability3);

    }

    private GideonBattleForged(final GideonBattleForged card) {
        super(card);
    }

    @Override
    public GideonBattleForged copy() {
        return new GideonBattleForged(this);
    }
}

class GideonBattleForgedToken extends TokenImpl {

    public GideonBattleForgedToken() {
        super("", "4/4 Human Soldier creature with indestructible");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(IndestructibleAbility.getInstance());
    }

    public GideonBattleForgedToken(final GideonBattleForgedToken token) {
        super(token);
    }

    public GideonBattleForgedToken copy() {
        return new GideonBattleForgedToken(this);
    }
}

class GideonBattleForgedAttacksIfAbleTargetEffect extends RequirementEffect {

    protected MageObjectReference targetPermanentReference;

    public GideonBattleForgedAttacksIfAbleTargetEffect(Duration duration) {
        super(duration);
        staticText = "Up to one target creature an opponent controls attacks {this} during its controller's next turn if able";
    }

    public GideonBattleForgedAttacksIfAbleTargetEffect(final GideonBattleForgedAttacksIfAbleTargetEffect effect) {
        super(effect);
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public GideonBattleForgedAttacksIfAbleTargetEffect copy() {
        return new GideonBattleForgedAttacksIfAbleTargetEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (targetPermanentReference == null) {
            return true;
        }
        Permanent targetPermanent = targetPermanentReference.getPermanent(game);
        if (targetPermanent == null) {
            return true;
        }
        return game.getTurnPhaseType() == TurnPhase.END && this.isYourNextTurn(game); // discard on end of their next turn
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getTargetPointer().getFirst(game, source) == null) {
            discard();
        } else {
            targetPermanentReference = new MageObjectReference(getTargetPointer().getFirst(game, source), game);
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.isActivePlayer(permanent.getControllerId())) {
                Permanent planeswalker = game.getPermanent(source.getSourceId());
                if (planeswalker != null) {
                    return true;
                } else {
                    discard();
                }
            }
        }
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getSourceId();
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
