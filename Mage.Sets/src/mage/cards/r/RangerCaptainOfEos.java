package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangerCaptainOfEos extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("a creature card with mana value 1 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public RangerCaptainOfEos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Ranger-Captain of Eos enters the battlefield, you may search your library for a creature card with converted mana cost 1 or less, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Sacrifice Ranger-Captain of Eos: Your opponents can't cast noncreature spells this turn.
        this.addAbility(new SimpleActivatedAbility(new RangerCaptainOfEosEffect(), new SacrificeSourceCost()));
    }

    private RangerCaptainOfEos(final RangerCaptainOfEos card) {
        super(card);
    }

    @Override
    public RangerCaptainOfEos copy() {
        return new RangerCaptainOfEos(this);
    }
}

class RangerCaptainOfEosEffect extends ContinuousRuleModifyingEffectImpl {

    RangerCaptainOfEosEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast noncreature spells this turn.";
    }

    private RangerCaptainOfEosEffect(final RangerCaptainOfEosEffect effect) {
        super(effect);
    }

    @Override
    public RangerCaptainOfEosEffect copy() {
        return new RangerCaptainOfEosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast noncreature spells this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.hasOpponent(event.getPlayerId(), game)) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
}
