package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author DominionSpy
 */
public final class TomikWielderOfLaw extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalkers");
    private static final Hint hint = new ValueHint("planeswalkers you control", new PermanentsOnBattlefieldCount(filter));

    public TomikWielderOfLaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Affinity for planeswalkers
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter))
                .addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever an opponent attacks with creatures, if two or more of those creatures are attacking you and/or planeswalkers you control, that opponent loses 3 life and you draw a card.
        this.addAbility(new TomikWielderOfLawTriggeredAbility());
    }

    private TomikWielderOfLaw(final TomikWielderOfLaw card) {
        super(card);
    }

    @Override
    public TomikWielderOfLaw copy() {
        return new TomikWielderOfLaw(this);
    }
}

class TomikWielderOfLawTriggeredAbility extends TriggeredAbilityImpl {

    TomikWielderOfLawTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(3));
        addEffect(new DrawCardSourceControllerEffect(1));
    }

    private TomikWielderOfLawTriggeredAbility(final TomikWielderOfLawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TomikWielderOfLawTriggeredAbility copy() {
        return new TomikWielderOfLawTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(getControllerId());
        if (player != null
                && player.hasOpponent(game.getActivePlayerId(), game)) {
            getEffects().setTargetPointer(new FixedTarget(game.getCombat().getAttackingPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game
                .getCombat()
                .getAttackers()
                .stream()
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .filter(getControllerId()::equals)
                .count() >= 2;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent attacks with creatures, " +
                "if two or more of those creatures are attacking you " +
                "and/or planeswalkers you control, that opponent loses 3 life " +
                "and you draw a card.";
    }
}
