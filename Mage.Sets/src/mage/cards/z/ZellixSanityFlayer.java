package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledCardsEvent;
import mage.game.permanent.token.Horror2Token;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZellixSanityFlayer extends CardImpl {

    public ZellixSanityFlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
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

class ZellixSanityFlayerTriggeredAbility extends TriggeredAbilityImpl {

    ZellixSanityFlayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new Horror2Token()));
        this.withFlavorWord("Hive Mind");
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
        return event.getType() == GameEvent.EventType.MILLED_CARDS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((MilledCardsEvent) event).getCards().count(StaticFilters.FILTER_CARD_CREATURE, game) > 0;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a player mills one or more creature cards, you";
    }
}
