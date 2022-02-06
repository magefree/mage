package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KelsFightFixer extends CardImpl {

    public KelsFightFixer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AZRA);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever you sacrifice a creature, you may pay {U/B}. If you do, draw a card.
        this.addAbility(new KelsFightFixerTriggeredAbility());

        // {1}, Sacrifice a creature: Kels, Fight Fixer gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private KelsFightFixer(final KelsFightFixer card) {
        super(card);
    }

    @Override
    public KelsFightFixer copy() {
        return new KelsFightFixer(this);
    }
}

class KelsFightFixerTriggeredAbility extends TriggeredAbilityImpl {

    KelsFightFixerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{U/B}")), false);
        setLeavesTheBattlefieldTrigger(true);
    }

    private KelsFightFixerTriggeredAbility(final KelsFightFixerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KelsFightFixerTriggeredAbility copy() {
        return new KelsFightFixerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD).isCreature(game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you sacrifice a creature, " ;
    }
}
