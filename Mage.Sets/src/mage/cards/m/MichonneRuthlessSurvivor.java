package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WalkerToken;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MichonneRuthlessSurvivor extends CardImpl {

    public MichonneRuthlessSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Michonne enters the battlefield, create two Walker tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WalkerToken(), 2)));

        // As long as Michonne is equipped, she must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new ConditionalRequirementEffect(
                new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield), EquippedSourceCondition.instance,
                "as long as {this} is equipped, she must be blocked if able"
        )));

        // Whenever Michonne and at least two Zombies attack, she gains indestructible until end of turn.
        this.addAbility(new MichonneRuthlessSurvivorAbility());
    }

    private MichonneRuthlessSurvivor(final MichonneRuthlessSurvivor card) {
        super(card);
    }

    @Override
    public MichonneRuthlessSurvivor copy() {
        return new MichonneRuthlessSurvivor(this);
    }
}

class MichonneRuthlessSurvivorAbility extends TriggeredAbilityImpl {

    public MichonneRuthlessSurvivorAbility() {
        super(Zone.BATTLEFIELD, new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
    }

    public MichonneRuthlessSurvivorAbility(final MichonneRuthlessSurvivorAbility ability) {
        super(ability);
    }

    @Override
    public MichonneRuthlessSurvivorAbility copy() {
        return new MichonneRuthlessSurvivorAbility(this);
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
                .contains(this.sourceId)
                && game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(permanent -> permanent.hasSubtype(SubType.ZOMBIE, game))
                .count() >= 2;
    }

    @Override
    public String getRule() {
        return "Whenever {this} and at least two Zombies attack, she gains indestructible until end of turn.";
    }
}
