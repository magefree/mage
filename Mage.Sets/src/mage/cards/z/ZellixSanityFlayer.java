package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledBatchForOnePlayerEvent;
import mage.game.events.MilledCardEvent;
import mage.game.permanent.token.Horror2Token;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZellixSanityFlayer extends CardImpl {

    public ZellixSanityFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Hive Mind — Whenever a player mills one or more creature cards, you create a 1/1 black Horror creature token.
        this.addAbility(new ZellixSanityFlayerTriggeredAbility());

        // {1}, {T}: Target player mills three cards.
        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(3), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private ZellixSanityFlayer(final ZellixSanityFlayer card) {
        super(card);
    }

    @Override
    public ZellixSanityFlayer copy() {
        return new ZellixSanityFlayer(this);
    }
}

class ZellixSanityFlayerTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<MilledCardEvent> {

    ZellixSanityFlayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new Horror2Token()));
        this.withFlavorWord("Hive Mind");
        setTriggerPhrase("Whenever a player mills one or more creature cards, you ");
    }

    private ZellixSanityFlayerTriggeredAbility(final ZellixSanityFlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ZellixSanityFlayerTriggeredAbility copy() {
        return new ZellixSanityFlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.MILLED_CARDS_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkEvent(MilledCardEvent event, Game game) {
        Card card = event.getCard(game);
        return card != null && card.isCreature(game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((MilledBatchForOnePlayerEvent) event, game).isEmpty();
    }
}
