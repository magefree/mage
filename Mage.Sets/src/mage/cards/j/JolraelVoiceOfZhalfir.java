package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JolraelVoiceOfZhalfir extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a land creature you control");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public JolraelVoiceOfZhalfir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, up to one target land you control becomes an X/X green and blue Bird creature with flying and haste until end of turn, where X is the number of cards in your hand. It's still a land.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new JolraelVoiceOfZhalfirEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND
        ));
        this.addAbility(ability);

        // Whenever a land creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter,
                false, SetTargetPointer.NONE, true
        ));
    }

    private JolraelVoiceOfZhalfir(final JolraelVoiceOfZhalfir card) {
        super(card);
    }

    @Override
    public JolraelVoiceOfZhalfir copy() {
        return new JolraelVoiceOfZhalfir(this);
    }
}

class JolraelVoiceOfZhalfirEffect extends OneShotEffect {

    JolraelVoiceOfZhalfirEffect() {
        super(Outcome.Benefit);
        staticText = "up to one target land you control becomes an X/X green and blue Bird creature with " +
                "flying and haste until end of turn, where X is the number of cards in your hand. It's still a land";
    }

    private JolraelVoiceOfZhalfirEffect(final JolraelVoiceOfZhalfirEffect effect) {
        super(effect);
    }

    @Override
    public JolraelVoiceOfZhalfirEffect copy() {
        return new JolraelVoiceOfZhalfirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = Optional
                .of(source)
                .map(Ability::getControllerId)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(count, count, "", SubType.BIRD)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.EndOfTurn
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
