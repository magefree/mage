package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class SanctifierEnVec extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards that are black or red");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new ColorPredicate(ObjectColor.RED)));
    }

    public SanctifierEnVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black and from red
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED));

        // When Sanctifier en-Vec enters the battlefield, exile all cards that are black or red from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileGraveyardAllPlayersEffect(filter)));

        // If a black or red permanent, spell, or card not on the battlefield would be put into a graveyard, exile it instead.
        this.addAbility(new SimpleStaticAbility(new SanctifierEnVecEffect()));
    }

    private SanctifierEnVec(final SanctifierEnVec card) {
        super(card);
    }

    @Override
    public SanctifierEnVec copy() {
        return new SanctifierEnVec(this);
    }
}

class SanctifierEnVecEffect extends ReplacementEffectImpl {

    public SanctifierEnVecEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        this.staticText = "If a black or red permanent, spell, or card not on the battlefield would be put into a graveyard, exile it instead";
    }

    private SanctifierEnVecEffect(final SanctifierEnVecEffect effect) {
        super(effect);
    }

    @Override
    public SanctifierEnVecEffect copy() {
        return new SanctifierEnVecEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null) {
                return permanent.getColor(game).contains(ObjectColor.BLACK) || permanent.getColor(game).contains(ObjectColor.RED);
            }
            Card card = game.getCard(zEvent.getTargetId());
            if (card != null) {
                return card.getColor(game).contains(ObjectColor.BLACK) || card.getColor(game).contains(ObjectColor.RED);
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }
}
