package mage.cards.g;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldbugScrappyScout extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("Human spells");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public GoldbugScrappyScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Human spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                filter, Duration.WhileOnBattlefield
        )));

        // Whenever Goldbug and at least one Human attack, draw a card and convert Goldbug.
        this.addAbility(new GoldbugScrappyScoutTriggeredAbility());
    }

    private GoldbugScrappyScout(final GoldbugScrappyScout card) {
        super(card);
    }

    @Override
    public GoldbugScrappyScout copy() {
        return new GoldbugScrappyScout(this);
    }
}

class GoldbugScrappyScoutTriggeredAbility extends TriggeredAbilityImpl {

    GoldbugScrappyScoutTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new TransformSourceEffect());
    }

    private GoldbugScrappyScoutTriggeredAbility(final GoldbugScrappyScoutTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoldbugScrappyScoutTriggeredAbility copy() {
        return new GoldbugScrappyScoutTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game
                .getCombat()
                .getAttackers()
                .contains(getSourceId())
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.HUMAN, game));
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least one Human attack, draw a card and convert {this}.";
    }
}
