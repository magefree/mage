package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.PlantToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurntimberSower extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("creatures");

    public TurntimberSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more land cards are put into your graveyard from anywhere, create a 0/1 green Plant creature token.
        this.addAbility(new TurntimberSowerTriggeredAbility());

        // {G}, Sacrifice three creatures: Return target land card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, filter)));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND));
        this.addAbility(ability);
    }

    private TurntimberSower(final TurntimberSower card) {
        super(card);
    }

    @Override
    public TurntimberSower copy() {
        return new TurntimberSower(this);
    }
}

class TurntimberSowerTriggeredAbility extends TriggeredAbilityImpl {

    public TurntimberSowerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new PlantToken()), false);
    }

    public TurntimberSowerTriggeredAbility(final TurntimberSowerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent != null
                && Zone.GRAVEYARD == zEvent.getToZone()
                && zEvent.getCards() != null) {
            for (Card card : zEvent.getCards()) {
                if (card != null) {
                    UUID cardOwnerId = card.getOwnerId();
                    if (cardOwnerId != null
                            && card.isOwnedBy(getControllerId())
                            && card.isLand(game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TurntimberSowerTriggeredAbility copy() {
        return new TurntimberSowerTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard "
                + "from anywhere, create a 0/1 green Plant creature token.";
    }
}
