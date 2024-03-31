package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.filter.predicate.mageobject.TargetsPlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.MercenaryToken;
import mage.game.stack.StackObject;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ErthaJoFrontierMentor extends CardImpl {

    public ErthaJoFrontierMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Ertha Jo, Frontier Mentor enters the battlefield, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MercenaryToken())));

        // Whenever you activate an ability that targets a creature or player, copy that ability. You may choose new targets for the copy.
        this.addAbility(new ErthaJoFrontierMentorTriggeredAbility());
    }

    private ErthaJoFrontierMentor(final ErthaJoFrontierMentor card) {
        super(card);
    }

    @Override
    public ErthaJoFrontierMentor copy() {
        return new ErthaJoFrontierMentor(this);
    }
}

// Some abstract type is confused there, so not using Predicates.or
enum ErthaJoFrontierMentorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    private static final TargetsPermanentPredicate permanentPredicate =
            new TargetsPermanentPredicate(StaticFilters.FILTER_PERMANENT_CREATURE);

    private static final TargetsPlayerPredicate playerPredicate = new TargetsPlayerPredicate();

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> o, Game game) {
        return permanentPredicate.apply(o, game) || playerPredicate.apply(o, game);
    }

    @Override
    public String toString() {
        return "Or(" + permanentPredicate + ", " + playerPredicate + ')';
    }
}

class ErthaJoFrontierMentorTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterStackObject filter = new FilterStackObject("ability that targets a creature or player");

    static {
        filter.add(ErthaJoFrontierMentorPredicate.instance);
    }

    public ErthaJoFrontierMentorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect(), false);
        this.addTarget(new TargetAnyTarget());
        setTriggerPhrase("Whenever you activate an ability that targets a creature or player, ");
    }

    private ErthaJoFrontierMentorTriggeredAbility(final ErthaJoFrontierMentorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ErthaJoFrontierMentorTriggeredAbility copy() {
        return new ErthaJoFrontierMentorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject == null
                || !event.getPlayerId().equals(getControllerId())
                || !filter.match(stackObject, getControllerId(), this, game)
        ) {
            return false;
        }
        // For the copy effect to find.
        this.getEffects().setValue("stackObject", stackObject);
        return true;
    }
}
