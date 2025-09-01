package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbflareGremlin extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("a player taps a land");

    public BarbflareGremlin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a player taps a land for mana, if Barbflare Gremlin is tapped, that player adds one mana of any type that land produced. Then that land deals 1 damage to that player.
        Ability ability = new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(), filter, SetTargetPointer.PERMANENT
        ).withInterveningIf(SourceTappedCondition.TAPPED);
        ability.addEffect(new BarbflareGremlinEffect());
        this.addAbility(ability);
    }

    private BarbflareGremlin(final BarbflareGremlin card) {
        super(card);
    }

    @Override
    public BarbflareGremlin copy() {
        return new BarbflareGremlin(this);
    }
}

class BarbflareGremlinEffect extends OneShotEffect {

    BarbflareGremlinEffect() {
        super(Outcome.Benefit);
        staticText = "Then that land deals 1 damage to that player";
    }

    private BarbflareGremlinEffect(final BarbflareGremlinEffect effect) {
        super(effect);
    }

    @Override
    public BarbflareGremlinEffect copy() {
        return new BarbflareGremlinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("tappedPermanent");
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null && player.damage(1, permanent.getId(), source, game) > 0;
    }
}
