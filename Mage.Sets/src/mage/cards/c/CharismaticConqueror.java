package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.IxalanVampireToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * Charismatic Conqueror {1}{W}
 * Creature - Vampire Soldier 2/2
 * Vigilance
 * Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.
 *
 * @author DominionSpy
 */
public final class CharismaticConqueror extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public CharismaticConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new CharismaticConquerorTriggeredAbility());
//        this.addAbility(new EntersBattlefieldOpponentTriggeredAbility(
//                Zone.BATTLEFIELD, new CharismaticConquerorEffect(), filter, false, SetTargetPointer.PERMANENT
//        ));
    }

    private CharismaticConqueror(final CharismaticConqueror card) {
        super(card);
    }

    @Override
    public CharismaticConqueror copy() {
        return new CharismaticConqueror(this);
    }
}

class CharismaticConquerorTriggeredAbility extends TriggeredAbilityImpl {

    CharismaticConquerorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CharismaticConquerorEffect(), false);
        setTriggerPhrase("Whenever an artifact or creature enters the battlefield untapped and under an opponent's control, ");
    }

    private CharismaticConquerorTriggeredAbility(final CharismaticConquerorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CharismaticConquerorTriggeredAbility copy() {
        return new CharismaticConquerorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        if (!permanent.isArtifact(game) && !permanent.isCreature(game)) {
            return false;
        }
        if (!game.getOpponents(getControllerId()).contains(permanent.getControllerId())) {
            return false;
        }

        this.getAllEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}

class CharismaticConquerorEffect extends OneShotEffect {

    CharismaticConquerorEffect() {
        super(Outcome.Benefit);
        this.staticText = "they may tap that permanent. If they don't, you create a 1/1 white Vampire creature token with lifelink.";
    }

    private CharismaticConquerorEffect(final CharismaticConquerorEffect effect) {
        super(effect);
    }

    @Override
    public CharismaticConquerorEffect copy() {
        return new CharismaticConquerorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null && game.getPlayer(permanent.getControllerId()) != null) {
            Player opponent = game.getPlayer(permanent.getControllerId());
            if (!permanent.isTapped() &&
                    opponent.chooseUse(Outcome.Tap, "Tap " + permanent.getLogName(), source, game)) {
                permanent.tap(source, game);
                return false;
            }
        }

        return new CreateTokenEffect(new IxalanVampireToken()).apply(game, source);
    }
}
