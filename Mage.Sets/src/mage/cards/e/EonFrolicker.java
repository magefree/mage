package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EonFrolicker extends CardImpl {

    public EonFrolicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Eon Frolicker enters the battlefield, if you cast it, target opponent takes an extra turn after this one. Until your next turn, you and planeswalkers you control gain protection from that player.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new EonFrolickerEffect()),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, target opponent takes an extra turn after this one. Until your next turn, " +
                "you and planeswalkers you control gain protection from that player."
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private EonFrolicker(final EonFrolicker card) {
        super(card);
    }

    @Override
    public EonFrolicker copy() {
        return new EonFrolicker(this);
    }
}

class EonFrolickerEffect extends OneShotEffect {

    EonFrolickerEffect() {
        super(Outcome.Benefit);
    }

    private EonFrolickerEffect(final EonFrolickerEffect effect) {
        super(effect);
    }

    @Override
    public EonFrolickerEffect copy() {
        return new EonFrolickerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        game.getState().getTurnMods().add(new TurnMod(player.getId()).withExtraTurn());
        FilterPlayer filter = new FilterPlayer(player.getName());
        filter.add(new PlayerIdPredicate(player.getId()));
        Ability ability = new ProtectionAbility(filter);
        game.addEffect(new GainAbilityControlledEffect(
                ability, Duration.UntilYourNextTurn,
                StaticFilters.FILTER_PERMANENT_PLANESWALKER
        ), source);
        game.addEffect(new GainAbilityControllerEffect(
                ability, Duration.UntilYourNextTurn
        ), source);
        return true;
    }
}
