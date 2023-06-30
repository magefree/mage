package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/*
 * @author LevelX2
 *
 *
 * 702.70. Champion
 *
 * 702.70a Champion represents two triggered abilities. "Champion an [object]" means
 * "When this permanent enters the battlefield, sacrifice it unless you exile another
 * [object] you control" and "When this permanent leaves the battlefield, return the
 * exiled card to the battlefield under its owner's control."
 *
 * 702.70b The two abilities represented by champion are linked. See rule 607, "Linked Abilities." #
 *
 * 702.70c A permanent is "championed" by another permanent if the latter exiles
 * the former as the direct result of a champion ability. #
 *
 */
public class ChampionAbility extends StaticAbility {

    protected final String objectDescription;

    /**
     * Champion one or more creature types or if the subtype array is empty
     * champion every creature.
     *
     * @param card
     * @param subtypes subtypes to champion with, if empty all creatures can be
     *                 used
     */
    public ChampionAbility(Card card, SubType... subtypes) {
        super(Zone.BATTLEFIELD, null);

        List<SubType> subTypes = Arrays.asList(subtypes);
        FilterControlledPermanent filter;
        switch (subTypes.size()) {
            case 0:
                this.objectDescription = "creature";
                filter = StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE;
                break;
            case 1:
                SubType subType = subTypes.get(0);
                this.objectDescription = subType.getDescription();
                filter = new FilterControlledPermanent(subType, "another " + subType);
                filter.add(AnotherPredicate.instance);
                break;
            case 2:
                SubType subType1 = subTypes.get(0);
                SubType subType2 = subTypes.get(1);
                this.objectDescription = subType1.getDescription() + " or " + subType2.getDescription();
                filter = new FilterControlledPermanent();
                filter.add(Predicates.or(
                        subType1.getPredicate(),
                        subType2.getPredicate()
                ));
                filter.add(AnotherPredicate.instance);
                break;
            default:
                throw new UnsupportedOperationException("can't have more than two subtypes currently");
        }

        // When this permanent enters the battlefield, sacrifice it unless you exile another [object] you control.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ChampionExileCost(filter)), false
        );
        ability1.setRuleVisible(false);
        addSubAbility(ability1);

        // When this permanent leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(
                new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false
        );
        ability2.setRuleVisible(false);
        addSubAbility(ability2);
    }

    public ChampionAbility(final ChampionAbility ability) {
        super(ability);
        this.objectDescription = ability.objectDescription;
    }

    @Override
    public ChampionAbility copy() {
        return new ChampionAbility(this);
    }

    @Override
    public String getRule() {
        return "Champion " + CardUtil.addArticle(objectDescription)
                + " <i>(When this enters the battlefield, sacrifice it unless you exile another " + objectDescription
                + " you control. When this leaves the battlefield, that card returns to the battlefield.)</i>";
    }
}

class ChampionExileCost extends CostImpl {

    ChampionExileCost(FilterControlledPermanent filter) {
        this.addTarget(new TargetControlledPermanent(1, 1, filter, true));
        this.text = "exile " + filter.getMessage() + " you control";
    }

    private ChampionExileCost(ChampionExileCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller == null || !targets.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        for (UUID targetId : targets.get(0).getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                return false;
            }
            paid |= controller.moveCardsToExile(
                    permanent, source, game, true,
                    CardUtil.getExileZoneId(game, source),
                    CardUtil.getSourceName(game, source)
            );
            if (paid) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_CHAMPIONED, permanent.getId(), source, controllerId));
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, source, game);
    }

    @Override
    public ChampionExileCost copy() {
        return new ChampionExileCost(this);
    }
}
