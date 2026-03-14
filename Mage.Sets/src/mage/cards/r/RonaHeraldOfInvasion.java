package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RonaHeraldOfInvasion extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a legendary spell");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public RonaHeraldOfInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{U}",
                "Rona, Tolarian Obliterator",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.WIZARD}, "UB"
        );

        // Rona, Herald of Invasion
        this.getLeftHalfCard().setPT(1, 3);

        // Whenever you cast a legendary spell, untap Rona, Herald of Invasion.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));

        // {T}: Draw a card, then discard a card.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(1, 1), new TapSourceCost()
        ));

        // {5}{B/P}: Transform Rona. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{B/P}")));

        // Rona, Tolarian Obliterator
        this.getRightHalfCard().setPT(5, 5);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Whenever a source deals damage to Rona, Tolarian Obliterator, that source's controller exiles a card from their hand at random. If it's a land card, you may put it onto the battlefield under your control. Otherwise, you may cast it without paying its mana cost.
        this.getRightHalfCard().addAbility(new RonaTolarianObliteratorTriggeredAbility());
    }

    private RonaHeraldOfInvasion(final RonaHeraldOfInvasion card) {
        super(card);
    }

    @Override
    public RonaHeraldOfInvasion copy() {
        return new RonaHeraldOfInvasion(this);
    }
}

class RonaTolarianObliteratorTriggeredAbility extends TriggeredAbilityImpl {

    RonaTolarianObliteratorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RonaTolarianObliteratorEffect());
    }

    private RonaTolarianObliteratorTriggeredAbility(final RonaTolarianObliteratorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RonaTolarianObliteratorTriggeredAbility copy() {
        return new RonaTolarianObliteratorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTarget(game.getControllerId(event.getSourceId())));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, that source's controller exiles a card " +
                "from their hand at random. If it's a land card, you may put it onto the battlefield " +
                "under your control. Otherwise, you may cast it without paying its mana cost.";
    }
}

class RonaTolarianObliteratorEffect extends OneShotEffect {

    RonaTolarianObliteratorEffect() {
        super(Outcome.Benefit);
    }

    private RonaTolarianObliteratorEffect(final RonaTolarianObliteratorEffect effect) {
        super(effect);
    }

    @Override
    public RonaTolarianObliteratorEffect copy() {
        return new RonaTolarianObliteratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getHand().getRandom(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isLand(game)) {
            return CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
        }
        return controller.chooseUse(Outcome.PutLandInPlay, "Put " + card.getIdName() + " onto the battlefield?", source, game)
                && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
