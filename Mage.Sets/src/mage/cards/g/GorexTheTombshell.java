package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GorexTheTombshell extends CardImpl {

    public GorexTheTombshell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, you may exile any number of creature cards from your graveyard. This spell costs {2} less to cast for each card exiled this way.
        Cost cost = new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_CREATURES, true);
        cost.setText("as an additional cost to cast this spell, you may exile any number of creature cards " +
                "from your graveyard. This spell costs {2} less to cast for each card exiled this way");
        this.getSpellAbility().addCost(cost);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new GorexTheTombshellCostReductionEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Gorex, the Tombshell attacks or dies, choose a card at random exiled with Gorex and put that card into its owner's hand.
        this.addAbility(new GorexTheTombshellTriggeredAbility());
    }

    private GorexTheTombshell(final GorexTheTombshell card) {
        super(card);
    }

    @Override
    public GorexTheTombshell copy() {
        return new GorexTheTombshell(this);
    }
}

class GorexTheTombshellCostReductionEffect extends CostModificationEffectImpl {

    GorexTheTombshellCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    private GorexTheTombshellCostReductionEffect(final GorexTheTombshellCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        for (Cost cost : spellAbility.getCosts()) {
            if (!(cost instanceof ExileXFromYourGraveCost)) {
                continue;
            }
            if (game.inCheckPlayableState()) {
                // allows to cast in getPlayable
                int reduction = ((ExileXFromYourGraveCost) cost).getMaxValue(spellAbility, game);
                CardUtil.adjustCost(spellAbility, reduction * 2);
            } else {
                // real cast
                int reduction = ((ExileXFromYourGraveCost) cost).getAmount();
                CardUtil.adjustCost(spellAbility, reduction * 2);
            }
            break;
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public GorexTheTombshellCostReductionEffect copy() {
        return new GorexTheTombshellCostReductionEffect(this);
    }
}

class GorexTheTombshellTriggeredAbility extends TriggeredAbilityImpl {

    GorexTheTombshellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GorexTheTombshellReturnEffect());
    }

    private GorexTheTombshellTriggeredAbility(final GorexTheTombshellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GorexTheTombshellTriggeredAbility copy() {
        return new GorexTheTombshellTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            return event.getSourceId().equals(getSourceId());
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && event.getTargetId().equals(getSourceId());
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks or dies, choose a card at random " +
                "exiled with {this} and put that card into its owner's hand.";
    }
}

class GorexTheTombshellReturnEffect extends OneShotEffect {

    GorexTheTombshellReturnEffect() {
        super(Outcome.Benefit);
    }

    private GorexTheTombshellReturnEffect(final GorexTheTombshellReturnEffect effect) {
        super(effect);
    }

    @Override
    public GorexTheTombshellReturnEffect copy() {
        return new GorexTheTombshellReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(
                game, source.getSourceId(), source.getSourceObjectZoneChangeCounter() - 1
        ));
        if (player == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        return player.moveCards(exileZone.getRandom(game), Zone.HAND, source, game);
    }
}
