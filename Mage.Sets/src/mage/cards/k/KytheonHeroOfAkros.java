package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KytheonHeroOfAkros extends TransformingDoubleFacedCard {

    public KytheonHeroOfAkros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{W}",
                "Gideon, Battle-Forged",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.GIDEON}, "W");

        // Kytheon, Hero of Akros
        this.getLeftHalfCard().setPT(2, 1);

        // At end of combat, if Kytheon, Hero of Akros and at least two other creatures attacked this combat, exile Kytheon, then return him to the battlefield transformed under his owner's control.
        Ability kytheonAbility = new EndOfCombatTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE), false
        ).withInterveningIf(KytheonHeroOfAkrosCondition.instance);
        kytheonAbility.addWatcher(new AttackedOrBlockedThisCombatWatcher());
        this.getLeftHalfCard().addAbility(kytheonAbility);

        // {2}{W}: Kytheon gains indestructible until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{W}")));

        // Gideon, Battle-Forged
        this.getRightHalfCard().setStartingLoyalty(3);

        // +2: Up to one target creature an opponent controls attacks Gideon, Battle-Forged during its controller's next turn if able.
        Ability ability = new LoyaltyAbility(new GideonBattleForgedEffect(), 2);
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.getRightHalfCard().addAbility(ability);

        // +1: Until your next turn, target creature gains indestructible. Untap that creature.
        ability = new LoyaltyAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setText("Until your next turn, target creature gains indestructible"), 1);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(ability);

        // 0: Until end of turn, Gideon, Battle-Forged becomes a 4/4 Human Soldier creature with indestructible that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        ability = new LoyaltyAbility(new BecomesCreatureSourceEffect(new CreatureToken(
                4, 4, "4/4 Human Soldier creature " +
                "with indestructible", SubType.HUMAN, SubType.SOLDIER
        ).withAbility(IndestructibleAbility.getInstance()), CardType.PLANESWALKER, Duration.EndOfTurn), 0);
        ability.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn)
                .setText("Prevent all damage that would be dealt to him this turn"));
        this.getRightHalfCard().addAbility(ability);
    }

    private KytheonHeroOfAkros(final KytheonHeroOfAkros card) {
        super(card);
    }

    @Override
    public KytheonHeroOfAkros copy() {
        return new KytheonHeroOfAkros(this);
    }
}

enum KytheonHeroOfAkrosCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
        return watcher != null
                && watcher
                .getAttackedThisTurnCreatures()
                .stream()
                .anyMatch(mor -> mor.refersTo(source, game))
                && watcher
                .getAttackedThisTurnCreatures()
                .size() >= 3;
    }

    @Override
    public String toString() {
        return "if {this} and at least two other creatures attacked this combat";
    }
}

class GideonBattleForgedEffect extends RequirementEffect {

    protected mage.MageObjectReference targetPermanentReference;

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
            targetPermanentReference = new mage.MageObjectReference(getTargetPointer().getFirst(game, source), game);
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
