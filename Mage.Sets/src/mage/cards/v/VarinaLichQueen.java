package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author TheElk801
 */
public final class VarinaLichQueen extends CardImpl {

    public VarinaLichQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you attack with one or more Zombies, draw that many cards, then discard that many cards. You gain that much life.
        this.addAbility(new VarinaLichQueenTriggeredAbility());

        // {2}, Exile two cards from your graveyard: Create a tapped 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(
                        new ZombieToken(),
                        1, true, false
                ), new GenericManaCost(2)
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD
        )));
        this.addAbility(ability);
    }

    private VarinaLichQueen(final VarinaLichQueen card) {
        super(card);
    }

    @Override
    public VarinaLichQueen copy() {
        return new VarinaLichQueen(this);
    }
}

class VarinaLichQueenTriggeredAbility extends TriggeredAbilityImpl {

    public VarinaLichQueenTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public VarinaLichQueenTriggeredAbility(final VarinaLichQueenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VarinaLichQueenTriggeredAbility copy() {
        return new VarinaLichQueenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int attackingZombies = 0;
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && creature.getControllerId() != null
                    && creature.isControlledBy(this.getControllerId())
                    && creature.hasSubtype(SubType.ZOMBIE, game)) {
                attackingZombies++;
            }
        }
        if (attackingZombies > 0) {
            this.getEffects().clear();
            addEffect(new DrawCardSourceControllerEffect(attackingZombies));
            addEffect(new DiscardControllerEffect(attackingZombies, false));
            addEffect(new GainLifeEffect(attackingZombies));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you attack with one or more Zombies, "
                + "draw that many cards, then discard that many cards. "
                + "You gain that much life.";
    }
}
