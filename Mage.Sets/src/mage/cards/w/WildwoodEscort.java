package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildwoodEscort extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or battle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public WildwoodEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Wildwood Escort enters the battlefield, return target creature or battle card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // If Wildwood Escort would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new WildwoodEscortEffect()));
    }

    private WildwoodEscort(final WildwoodEscort card) {
        super(card);
    }

    @Override
    public WildwoodEscort copy() {
        return new WildwoodEscort(this);
    }
}

class WildwoodEscortEffect extends ReplacementEffectImpl {

    public WildwoodEscortEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "if {this} would die, exile it instead";
    }

    public WildwoodEscortEffect(final WildwoodEscortEffect effect) {
        super(effect);
    }

    @Override
    public WildwoodEscortEffect copy() {
        return new WildwoodEscortEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!event.getTargetId().equals(source.getSourceId())) {
            return false;
        }
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        return zce.isDiesEvent();
    }
}
