package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class VulpineHarvester extends CardImpl {

    public VulpineHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more Phyrexians you control attack, return target artifact card from your graveyard to the battlefield if its mana value is less than or equal to their total power.
        this.addAbility(new VulpineHarvesterTriggeredAbility());
    }

    private VulpineHarvester(final VulpineHarvester card) {
        super(card);
    }

    @Override
    public VulpineHarvester copy() {
        return new VulpineHarvester(this);
    }
}

class VulpineHarvesterTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.PHYREXIAN);

    VulpineHarvesterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VulpineHarvesterEffect());
        this.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
    }

    private VulpineHarvesterTriggeredAbility(final VulpineHarvesterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VulpineHarvesterTriggeredAbility copy() {
        return new VulpineHarvesterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<Permanent> permanents = game
                .getCombat()
                .getAttackers()
                .stream()
                .map(game::getPermanent)
                .filter(permanent -> filter.match(permanent, getControllerId(), this, game))
                .collect(Collectors.toList());
        if (permanents.size() > 0) {
            this.getEffects().setValue("attackers", permanents);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Phyrexians you control attack, return target artifact card " +
                "from your graveyard to the battlefield if its mana value is less than or equal to their total power.";
    }
}

class VulpineHarvesterEffect extends OneShotEffect {

    VulpineHarvesterEffect() {
        super(Outcome.Benefit);
    }

    private VulpineHarvesterEffect(final VulpineHarvesterEffect effect) {
        super(effect);
    }

    @Override
    public VulpineHarvesterEffect copy() {
        return new VulpineHarvesterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        List<Permanent> attackers = (List<Permanent>) getValue("attackers");
        return player != null
                && card != null
                && attackers != null
                && card
                .getManaValue()
                <= attackers
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum()
                && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
