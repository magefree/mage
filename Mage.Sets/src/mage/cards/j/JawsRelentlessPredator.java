package mage.cards.j;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JawsRelentlessPredator extends CardImpl {

    public JawsRelentlessPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Jaws deals combat damage to a player, create that many Blood tokens.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new BloodToken(), SavedDamageValue.MANY)
        ));

        // Whenever a noncreature artifact is sacrificed or destroyed, Jaws deals 1 damage to each opponent.
        this.addAbility(new JawsRelentlessPredatorTriggeredAbility());
    }

    private JawsRelentlessPredator(final JawsRelentlessPredator card) {
        super(card);
    }

    @Override
    public JawsRelentlessPredator copy() {
        return new JawsRelentlessPredator(this);
    }
}

class JawsRelentlessPredatorTriggeredAbility extends TriggeredAbilityImpl {

    JawsRelentlessPredatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT));
        setTriggerPhrase("Whenever a noncreature artifact is sacrificed or destroyed, ");
    }

    private JawsRelentlessPredatorTriggeredAbility(final JawsRelentlessPredatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JawsRelentlessPredatorTriggeredAbility copy() {
        return new JawsRelentlessPredatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SACRIFICED_PERMANENT:
            case DESTROYED_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && !permanent.isCreature(game) && permanent.isArtifact(game);
    }
}
