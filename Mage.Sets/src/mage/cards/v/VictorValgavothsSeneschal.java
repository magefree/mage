package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VictorValgavothsSeneschal extends CardImpl {

    public VictorValgavothsSeneschal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, surveil 2 if this is the first time this ability has resolved this turn. If it's the second time, each opponent discards a card. If it's the third time, put a creature card from a graveyard onto the battlefield under your control.
        Ability ability = new EerieAbility(
                new IfAbilityHasResolvedXTimesEffect(1, new SurveilEffect(2))
                        .setText("surveil 2 if this is the first time this ability has resolved this turn")
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                2, new DiscardEachPlayerEffect(TargetController.OPPONENT)
        ).setText("If it's the second time, each opponent discards a card"));
        ability.addEffect(new VictorValgavothsSeneschalEffect());
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private VictorValgavothsSeneschal(final VictorValgavothsSeneschal card) {
        super(card);
    }

    @Override
    public VictorValgavothsSeneschal copy() {
        return new VictorValgavothsSeneschal(this);
    }
}

class VictorValgavothsSeneschalEffect extends OneShotEffect {

    VictorValgavothsSeneschalEffect() {
        super(Outcome.Benefit);
        staticText = "If it's the third time, put a creature card " +
                "from a graveyard onto the battlefield under your control";
    }

    private VictorValgavothsSeneschalEffect(final VictorValgavothsSeneschalEffect effect) {
        super(effect);
    }

    @Override
    public VictorValgavothsSeneschalEffect copy() {
        return new VictorValgavothsSeneschalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (AbilityResolvedWatcher.getResolutionCount(game, source) != 3) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.withNotTarget(true);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
