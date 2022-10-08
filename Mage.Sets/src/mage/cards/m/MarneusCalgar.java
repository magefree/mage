package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.PermanentImpl;
import mage.game.permanent.token.AstartesWarriorToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MarneusCalgar extends CardImpl {

    public MarneusCalgar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASTARTES, SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Master Tactician — Whenever one or more tokens enter the battlefield under your control, draw a card.
        this.addAbility(new MarneusCalgarTriggeredAbility());

        // Chapter Master — {6}: Create two 2/2 white Astartes Warrior creature tokens with vigilance.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new AstartesWarriorToken(), 2),
                new ManaCostsImpl<>("{6}")
        ).withFlavorWord("Chapter Master"));
    }

    private MarneusCalgar(final MarneusCalgar card) {
        super(card);
    }

    @Override
    public MarneusCalgar copy() {
        return new MarneusCalgar(this);
    }
}

class MarneusCalgarTriggeredAbility extends TriggeredAbilityImpl {

    MarneusCalgarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
        this.withFlavorWord("Master Tactician");
        setTriggerPhrase("Whenever one or more tokens enter the battlefield under your control, ");
    }

    private MarneusCalgarTriggeredAbility(final MarneusCalgarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return Zone.BATTLEFIELD == zEvent.getToZone()
                && zEvent.getTokens() != null
                && zEvent
                .getTokens()
                .stream()
                .filter(Objects::nonNull)
                .map(PermanentImpl::getControllerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public MarneusCalgarTriggeredAbility copy() {
        return new MarneusCalgarTriggeredAbility(this);
    }
}
