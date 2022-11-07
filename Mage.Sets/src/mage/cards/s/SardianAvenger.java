package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SardianAvenger extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public SardianAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Sardian Avenger attacks, it gets +X/+0 until end of turn, where X is the number of artifacts your opponents control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"
        )));

        // Whenever an artifact an opponent controls is put into a graveyard from the battlefield, Sardian Avenger deals 1 damage to that player.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new SardianAvengerEffect(), false, filter
        ).setTriggerPhrase("Whenever an artifact an opponent controls is put into a graveyard from the battlefield, "));
    }

    private SardianAvenger(final SardianAvenger card) {
        super(card);
    }

    @Override
    public SardianAvenger copy() {
        return new SardianAvenger(this);
    }
}

class SardianAvengerEffect extends OneShotEffect {

    SardianAvengerEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 1 damage to that player";
    }

    private SardianAvengerEffect(final SardianAvengerEffect effect) {
        super(effect);
    }

    @Override
    public SardianAvengerEffect copy() {
        return new SardianAvengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable((Permanent) getValue("creatureDied"))
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.damage(1, source, game));
        return true;
    }
}
