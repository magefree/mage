package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class CrypticPursuit extends CardImpl {

    private static final FilterPermanent filterFaceDownPermanent = new FilterControlledCreaturePermanent("a face-down creature");
    static {
        filterFaceDownPermanent.add(FaceDownPredicate.instance);
    }

    public CrypticPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{R}");

        // Whenever you cast an instant or sorcery spell from your hand, manifest the top card of your library.
        // (Put that card onto the battlefield face down as a 2/2 creature.
        // Turn it face up any time for its mana cost if it’s a creature card.)
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ManifestEffect(1),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false,
                Zone.HAND)
        );

        // Whenever a face-down creature you control dies, exile it if it’s an instant or sorcery card.
        // You may cast that card until the end of your next turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CrypticPursuitExileAndPlayEffect(),
                false,
                filterFaceDownPermanent)
        );
    }

    private CrypticPursuit(final CrypticPursuit card) {
        super(card);
    }

    @Override
    public CrypticPursuit copy() {
        return new CrypticPursuit(this);
    }
}

class CrypticPursuitExileAndPlayEffect extends OneShotEffect {

    CrypticPursuitExileAndPlayEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it if it's an instant or sorcery card. " +
                          "You may cast that card until the end of your next turn.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Object diedObject = getValue("creatureDied");
        if (controller == null || !(diedObject instanceof Permanent)) {
            return false;
        }
        Permanent diedFaceDownCreature = (Permanent) diedObject;

        Card card = game.getCard(diedFaceDownCreature.getMainCard().getId());
        if (card == null || !card.isInstantOrSorcery(game)) {
            return false;
        }

        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
                game, source, card, TargetController.YOU,
                Duration.UntilEndOfYourNextTurn,
                false, false, true
        );

        return false;
    }

    CrypticPursuitExileAndPlayEffect(final CrypticPursuitExileAndPlayEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new CrypticPursuitExileAndPlayEffect(this);
    }
}