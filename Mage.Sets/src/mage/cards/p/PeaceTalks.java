package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class PeaceTalks extends CardImpl {

    public PeaceTalks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // This turn and next turn, creatures can't attack, and players and permanents can't be the targets of spells or activated abilities.
        this.getSpellAbility().addEffect(new PeaceTalksEffect());
    }

    private PeaceTalks(final PeaceTalks card) {
        super(card);
    }

    @Override
    public PeaceTalks copy() {
        return new PeaceTalks(this);
    }
}

class PeaceTalksEffect extends OneShotEffect {

    public PeaceTalksEffect() {
        super(Outcome.Neutral);
        this.staticText = "This turn and next turn, creatures can't attack,"
                + "and players and permanents can't be the targets of spells "
                + "or activated abilities";
    }

    public PeaceTalksEffect(final PeaceTalksEffect effect) {
        super(effect);
    }

    @Override
    public PeaceTalksEffect copy() {
        return new PeaceTalksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RestrictionEffect effect = new PeaceTalksCantAttackEffect();
        game.addEffect(effect, source);
        ContinuousRuleModifyingEffect effect2 = new PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities();
        game.addEffect(effect2, source);
        return true;
    }
}

class PeaceTalksCantAttackEffect extends RestrictionEffect {

    int startedTurnNum = 0;

    public PeaceTalksCantAttackEffect() {
        super(Duration.Custom);
        staticText = "Creatures can't attack this turn and next turn";
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        startedTurnNum = game.getTurnNum();
    }

    public PeaceTalksCantAttackEffect(final PeaceTalksCantAttackEffect effect) {
        super(effect);
        this.startedTurnNum = effect.startedTurnNum;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isCreature(game);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public PeaceTalksCantAttackEffect copy() {
        return new PeaceTalksCantAttackEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getTurnNum() > (startedTurnNum + 1)) {
            this.discard();
            return true;
        }
        return false;
    }
}

class PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities extends ContinuousRuleModifyingEffectImpl {

    int startedTurnNum = 0;

    public PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "players and permanents can't be the targets of spells or activated abilities";
    }

    public PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities(final PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities effect) {
        super(effect);
        this.startedTurnNum = effect.startedTurnNum;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        startedTurnNum = game.getTurnNum();
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (event.getTargetId().equals(playerId)) {
                return true;
            }
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (event.getTargetId().equals(permanent.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities copy() {
        return new PeaceTalksPlayersAndPermanentsCantBeTargetsOfSpellsOrActivatedAbilities(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getTurnNum() > (startedTurnNum + 1)) {
            this.discard();
            return true;
        }
        return false;
    }
}
