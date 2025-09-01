package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GideonBattleForged extends CardImpl {

    public GideonBattleForged(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.color.setWhite(true);

        this.nightCard = true;

        this.setStartingLoyalty(3);

        // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.
        Ability ability = new LoyaltyAbility(new GideonBattleForgedEffect(), 2);
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability);

        // +1: Until your next turn, target creature gains indestructible. Untap that creature.
        ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Until your next turn, target creature gains indestructible"), 1);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // 0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new CreatureToken(
                4, 4, "4/4 Human Soldier creature " +
                "with indestructible", SubType.HUMAN, SubType.SOLDIER
        ).withAbility(IndestructibleAbility.getInstance()), CardType.PLANESWALKER, Duration.EndOfTurn), 0);
        ability.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn)
                .setText("Prevent all damage that would be dealt to him this turn"));
        this.addAbility(ability);
    }

    private GideonBattleForged(final GideonBattleForged card) {
        super(card);
    }

    @Override
    public GideonBattleForged copy() {
        return new GideonBattleForged(this);
    }
}

class GideonBattleForgedEffect extends RequirementEffect {

    protected MageObjectReference targetPermanentReference;

    GideonBattleForgedEffect() {
        super(Duration.Custom);
        staticText = "up to one target creature an opponent controls attacks {this} during its controller's next turn if able";
    }

    private GideonBattleForgedEffect(final GideonBattleForgedEffect effect) {
        super(effect);
        this.targetPermanentReference = effect.targetPermanentReference;
    }

    @Override
    public GideonBattleForgedEffect copy() {
        return new GideonBattleForgedEffect(this);
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
        if (!permanent.getId().equals(getTargetPointer().getFirst(game, source))
                || !game.isActivePlayer(permanent.getControllerId())) {
            return false;
        }
        Permanent planeswalker = source.getSourcePermanentIfItStillExists(game);
        if (planeswalker == null) {
            discard();
            return false;
        }
        return true;
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
