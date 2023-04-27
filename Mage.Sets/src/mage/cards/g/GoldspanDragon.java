package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author weirddan455
 */
public final class GoldspanDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.TREASURE, "Treasures");

    public GoldspanDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Goldspan Dragon attacks or becomes the target of a spell, create a Treasure token.
        this.addAbility(new GoldspanDragonTriggeredAbility());

        // Treasures you control have "{T}, Sacrifice this artifact: Add two mana of any one color."
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        Cost cost = new SacrificeSourceCost();
        cost.setText("sacrifice this artifact");
        ability.addCost(cost);
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(ability, Duration.WhileOnBattlefield, filter)));
    }

    private GoldspanDragon(final GoldspanDragon card) {
        super(card);
    }

    @Override
    public GoldspanDragon copy() {
        return new GoldspanDragon(this);
    }
}

class GoldspanDragonTriggeredAbility extends TriggeredAbilityImpl {

    GoldspanDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever {this} attacks or becomes the target of a spell, ");
    }

    private GoldspanDragonTriggeredAbility(final GoldspanDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS
                || event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case DECLARED_ATTACKERS:
                return game.getCombat().getAttackers().contains(this.getSourceId());
            case TARGETED:
                if (event.getTargetId().equals(getSourceId())) {
                    StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
                    return sourceObject instanceof Spell;
                }
            default:
                return false;
        }
    }

    @Override
    public GoldspanDragonTriggeredAbility copy() {
        return new GoldspanDragonTriggeredAbility(this);
    }
}
