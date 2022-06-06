package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildswornProwler extends CardImpl {

    public GuildswornProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Guildsworn Prowler dies, if it wasn't blocking, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                GuildswornProwlerCondition.instance, "When {this} dies, if it wasn't blocking, draw a card."
        ));
    }

    private GuildswornProwler(final GuildswornProwler card) {
        super(card);
    }

    @Override
    public GuildswornProwler copy() {
        return new GuildswornProwler(this);
    }
}

enum GuildswornProwlerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .of(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> !BlockingOrBlockedWatcher.check(permanent, game))
                .orElse(false);
    }
}