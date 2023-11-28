package mage.cards.p;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.other.PlayerWithTheMostLifePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.IxalanVampireToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PreacherOfTheSchism extends CardImpl {

    public PreacherOfTheSchism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Preacher of the Schism attacks the player with the most life or tied for most life, create a 1/1 white Vampire creature token with lifelink.
        this.addAbility(new PreacherOfTheSchismFirstTrigger());

        // Whenever Preacher of the Schism attacks while you have the most life or are tied for most life, you draw a card and you lose 1 life.
        this.addAbility(new PreacherOfTheSchismSecondTrigger());
    }

    private PreacherOfTheSchism(final PreacherOfTheSchism card) {
        super(card);
    }

    @Override
    public PreacherOfTheSchism copy() {
        return new PreacherOfTheSchism(this);
    }
}

class PreacherOfTheSchismFirstTrigger extends TriggeredAbilityImpl {

    public PreacherOfTheSchismFirstTrigger() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new IxalanVampireToken()), false);
        setTriggerPhrase("Whenever {this} attacks the player with the most life or tied for most life, ");
    }

    protected PreacherOfTheSchismFirstTrigger(final PreacherOfTheSchismFirstTrigger ability) {
        super(ability);
    }

    @Override
    public PreacherOfTheSchismFirstTrigger copy() {
        return new PreacherOfTheSchismFirstTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId == null) {
            return false;
        }

        Player attackedPlayer = game.getPlayer(defenderId);
        return PlayerWithTheMostLifePredicate.instance.apply(
                new ObjectSourcePlayer<>(attackedPlayer, getControllerId(), null),
                game
        );
    }
}

class PreacherOfTheSchismSecondTrigger extends TriggeredAbilityImpl {

    public PreacherOfTheSchismSecondTrigger() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1, "you"), false);
        this.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        setTriggerPhrase("Whenever {this} attacks while you have the most life or are tied for most life, ");
    }

    protected PreacherOfTheSchismSecondTrigger(final PreacherOfTheSchismSecondTrigger ability) {
        super(ability);
    }

    @Override
    public PreacherOfTheSchismSecondTrigger copy() {
        return new PreacherOfTheSchismSecondTrigger(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID defenderId = game.getCombat().getDefenderId(getSourceId());
        if (defenderId == null) { // Checks that {this} is attacking.
            return false;
        }

        Player controller = game.getPlayer(getControllerId());
        return PlayerWithTheMostLifePredicate.instance.apply(
                new ObjectSourcePlayer<>(controller, getControllerId(), null),
                game
        );
    }
}
