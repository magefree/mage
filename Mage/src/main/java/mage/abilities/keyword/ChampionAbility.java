
package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

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

    protected EnumSet<SubType> subtypes;
    protected String objectDescription;

    public ChampionAbility(Card card, SubType subtype, boolean requiresCreature) {
        this(card, EnumSet.of(subtype), requiresCreature);
    }

    public ChampionAbility(Card card, boolean requiresCreature) {
        this(card, EnumSet.noneOf(SubType.class), requiresCreature);
    }

    /**
     * Champion one or more creature types or if the subtype array is empty
     * champion every creature.
     *
     * @param card
     * @param subtypes subtypes to champion with, if empty all creatures can be
     * used
     * @param requiresCreature for cards that specifically require championing
     * another creature
     */
    public ChampionAbility(Card card, EnumSet<SubType> subtypes, boolean requiresCreature) {
        super(Zone.BATTLEFIELD, null);

        this.subtypes = subtypes;
        StringBuilder sb = new StringBuilder("another ");
        List<Predicate<MageObject>> subtypesPredicates = new ArrayList<>();
        if (!subtypes.isEmpty()) {
            int i = 0;
            for (SubType subtype : this.subtypes) {
                subtypesPredicates.add(subtype.getPredicate());
                if (i == 0) {
                    sb.append(subtype);
                } else {
                    sb.append(" or ").append(subtype);
                }
                i++;
            }
        } else {
            sb.append("creature");
        }
        this.objectDescription = sb.toString();
        FilterControlledPermanent filter = new FilterControlledPermanent(objectDescription);
        if (!subtypesPredicates.isEmpty()) {
            filter.add(Predicates.or(subtypesPredicates));
        }
        if (requiresCreature) {
            filter.add(CardType.CREATURE.getPredicate());
        }
        filter.add(AnotherPredicate.instance);

        // When this permanent enters the battlefield, sacrifice it unless you exile another [object] you control.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ChampionExileCost(filter, card.getName() + " championed permanents")), false);
        ability1.setRuleVisible(false);
        addSubAbility(ability1);

        // When this permanent leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        ability2.setRuleVisible(false);
        addSubAbility(ability2);
    }

    public ChampionAbility(final ChampionAbility ability) {
        super(ability);
        this.subtypes = ability.subtypes;
        this.objectDescription = ability.objectDescription;
    }

    @Override
    public ChampionAbility copy() {
        return new ChampionAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Champion ").append(objectDescription);
        sb.append(" <i>(When this enters the battlefield, sacrifice it unless you exile another ");
        sb.append(objectDescription);
        sb.append(" you control. When this leaves the battlefield, that card returns to the battlefield.)</i>");
        return sb.toString();
    }
}

class ChampionExileCost extends CostImpl {

    private String exileZone;

    public ChampionExileCost(FilterControlledPermanent filter, String exileZone) {
        this.addTarget(new TargetControlledPermanent(1, 1, filter, true));
        this.text = "exile " + filter.getMessage() + " you control";
        this.exileZone = exileZone;
    }

    public ChampionExileCost(ChampionExileCost cost) {
        super(cost);
        this.exileZone = cost.exileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = ability.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (targets.choose(Outcome.Exile, controllerId, source.getSourceId(), game)) {
                UUID exileId = CardUtil.getExileZoneId(game, ability.getSourceId(), ability.getSourceObjectZoneChangeCounter()); // exileId important for return effect
                for (UUID targetId : targets.get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent == null) {
                        return false;
                    }
                    paid |= controller.moveCardToExileWithInfo(permanent, exileId, sourceObject.getIdName() + " championed permanents", source, game, Zone.BATTLEFIELD, true);
                    if (paid) {
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CREATURE_CHAMPIONED, permanent.getId(), source, controllerId));
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return targets.canChoose(source.getSourceId(), controllerId, game);
    }

    @Override
    public ChampionExileCost copy() {
        return new ChampionExileCost(this);
    }
}
