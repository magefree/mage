package mage.cards.j;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JinSakaiGhostOfTsushima extends CardImpl {

    public JinSakaiGhostOfTsushima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Jin Sakai deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Whenever a creature you control attacks a player, if no other creatures are attacking that player, choose one --
        // * Standoff -- It gains double strike until end of turn.
        // * Ghost -- It can't be blocked this turn.
        this.addAbility(new JinSakaiGhostOfTsushimaTriggeredAbility());
    }

    private JinSakaiGhostOfTsushima(final JinSakaiGhostOfTsushima card) {
        super(card);
    }

    @Override
    public JinSakaiGhostOfTsushima copy() {
        return new JinSakaiGhostOfTsushima(this);
    }
}

class JinSakaiGhostOfTsushimaTriggeredAbility extends TriggeredAbilityImpl {

    JinSakaiGhostOfTsushimaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()).withTargetDescription("it"));
        this.withFirstModeFlavorWord("Standoff");
        this.addMode(new Mode(new CantBeBlockedTargetEffect().withTargetDescription("it")).withFlavorWord("Ghost"));
        this.setTriggerPhrase("Whenever a creature you control attacks a player, if no other creatures are attacking that player, ");
    }

    private JinSakaiGhostOfTsushimaTriggeredAbility(final JinSakaiGhostOfTsushimaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JinSakaiGhostOfTsushimaTriggeredAbility copy() {
        return new JinSakaiGhostOfTsushimaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null
                || !permanent.isControlledBy(getControllerId())
                || game.getPlayer(game.getCombat().getDefenderId(permanent.getId())) == null) {
            return false;
        }
        this.getAllEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return this
                .getAllEffects()
                .stream()
                .map(Effect::getTargetPointer)
                .map(targetPointer -> targetPointer.getFirst(game, this))
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .findAny()
                .map(MageItem::getId)
                .map(game.getCombat()::getDefenderId)
                .filter(defenderId -> game
                        .getCombat()
                        .getAttackers()
                        .stream()
                        .map(game.getCombat()::getDefenderId)
                        .filter(defenderId::equals)
                        .count() == 1)
                .isPresent();
    }
}
