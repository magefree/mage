package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gloomshrieker extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public Gloomshrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Gloomshrieker enters the battlefield, return target permanent card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // If Gloomshrieker would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GloomshriekerEffect()));
    }

    private Gloomshrieker(final Gloomshrieker card) {
        super(card);
    }

    @Override
    public Gloomshrieker copy() {
        return new Gloomshrieker(this);
    }
}

class GloomshriekerEffect extends ReplacementEffectImpl {

    GloomshriekerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If {this} would die, exile it instead";
    }

    private GloomshriekerEffect(final GloomshriekerEffect effect) {
        super(effect);
    }

    @Override
    public GloomshriekerEffect copy() {
        return new GloomshriekerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId()) && ((ZoneChangeEvent) event).isDiesEvent();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return true;
    }
}
