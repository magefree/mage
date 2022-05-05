package mage.cards.p;

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
 * @author LevelX2
 */
public final class PossessedSkaab extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant, sorcery, or creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public PossessedSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Possessed Skaab enters the battlefield, return target instant, sorcery, or creature card from your graveyard to your hand.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // If Possessed Skaab would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(new PossessedSkaabDiesEffect()));
    }

    private PossessedSkaab(final PossessedSkaab card) {
        super(card);
    }

    @Override
    public PossessedSkaab copy() {
        return new PossessedSkaab(this);
    }
}

class PossessedSkaabDiesEffect extends ReplacementEffectImpl {

    public PossessedSkaabDiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would die, exile it instead";
    }

    public PossessedSkaabDiesEffect(final PossessedSkaabDiesEffect effect) {
        super(effect);
    }

    @Override
    public PossessedSkaabDiesEffect copy() {
        return new PossessedSkaabDiesEffect(this);
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
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            return zce.isDiesEvent();
        }
        return false;
    }
}
