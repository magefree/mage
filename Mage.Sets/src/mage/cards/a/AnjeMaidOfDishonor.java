package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.BloodToken;

/**
 *
 * @author weirddan455
 */
public final class AnjeMaidOfDishonor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature or a Blood token");

    static {
        filter.add(AnjeMaidOfDishonorPredicate.instance);
    }

    public AnjeMaidOfDishonor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever Anje, Maid of Dishonor and/or one or more other Vampires enter the battlefield under your control, create a Blood token.
        // This ability triggers only once each turn.
        this.addAbility(new AnjeMaidOfDishonorTriggeredAbility());

        // {2}, Sacrifice another creature or a Blood token: Each opponent loses 2 life and you gain 2 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(2), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private AnjeMaidOfDishonor(final AnjeMaidOfDishonor card) {
        super(card);
    }

    @Override
    public AnjeMaidOfDishonor copy() {
        return new AnjeMaidOfDishonor(this);
    }
}

class AnjeMaidOfDishonorTriggeredAbility extends TriggeredAbilityImpl {

    public AnjeMaidOfDishonorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BloodToken()));
        this.setTriggersOnce(true);
    }

    private AnjeMaidOfDishonorTriggeredAbility(final AnjeMaidOfDishonorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnjeMaidOfDishonorTriggeredAbility copy() {
        return new AnjeMaidOfDishonorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isControlledBy(controllerId)) {
            return permanent.getId().equals(sourceId) || permanent.hasSubtype(SubType.VAMPIRE, game);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} and/or one or more other Vampires enter the battlefield under your control, ";
    }
}

enum AnjeMaidOfDishonorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        MageObject object = input.getObject();
        if (object instanceof PermanentToken && object.hasSubtype(SubType.BLOOD, game)) {
            return true;
        }
        if (!object.getId().equals(input.getSourceId())) {
            return object.isCreature(game);
        }
        return false;
    }
}
