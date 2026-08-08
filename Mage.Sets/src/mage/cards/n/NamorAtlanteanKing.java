package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MerfolkToken;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class NamorAtlanteanKing extends CardImpl {

    public NamorAtlanteanKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, create a 1/1 blue Merfolk creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CreateTokenEffect(new MerfolkToken()),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // Whenever Namor attacks a player who has more life than you, other creatures you control attacking that player get +2/+0 until end of turn.
        this.addAbility(new NamorAtlanteanKingTriggeredAbility());
    }

    private NamorAtlanteanKing(final NamorAtlanteanKing card) {
        super(card);
    }

    @Override
    public NamorAtlanteanKing copy() {
        return new NamorAtlanteanKing(this);
    }
}

class NamorAtlanteanKingTriggeredAbility extends TriggeredAbilityImpl {

    NamorAtlanteanKingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NamorAtlanteanKingEffect(), false);
        setTriggerPhrase("Whenever {this} attacks a player who has more life than you, ");
    }

    private NamorAtlanteanKingTriggeredAbility(final NamorAtlanteanKingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NamorAtlanteanKingTriggeredAbility copy() {
        return new NamorAtlanteanKingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())) {
            return false;
        }
        Player controller = game.getPlayer(getControllerId());
        Player defendingPlayer = game.getPlayer(event.getTargetId());
        if (controller == null || defendingPlayer == null || defendingPlayer.getLife() <= controller.getLife()) {
            return false;
        }
        for (Effect effect : getEffects()) {
            effect.setTargetPointer(new FixedTarget(defendingPlayer.getId(), game));
        }
        return true;
    }
}

class NamorAtlanteanKingEffect extends OneShotEffect {

    NamorAtlanteanKingEffect() {
        super(Outcome.BoostCreature);
        staticText = "other creatures you control attacking that player get +2/+0 until end of turn";
    }

    private NamorAtlanteanKingEffect(final NamorAtlanteanKingEffect effect) {
        super(effect);
    }

    @Override
    public NamorAtlanteanKingEffect copy() {
        return new NamorAtlanteanKingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = getTargetPointer().getFirst(game, source);
        if (defendingPlayerId == null) {
            return false;
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(
            StaticFilters.FILTER_ATTACKING_CREATURE, source.getControllerId(), source, game
        )) {
            if (permanent.getId().equals(source.getSourceId())) {
                continue;
            }
            UUID permanentDefendingPlayerId = game.getCombat().getDefendingPlayerId(permanent.getId(), game);
            if (defendingPlayerId.equals(permanentDefendingPlayerId)) {
                game.addEffect(new BoostTargetEffect(2, 0).setTargetPointer(new FixedTarget(permanent, game)), source);
            }
        }

        return true;
    }
}
