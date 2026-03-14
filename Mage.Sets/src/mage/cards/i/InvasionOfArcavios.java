package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfArcavios extends TransformingDoubleFacedCard {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand");

    static {
        filter.add(new CastFromZonePredicate(Zone.HAND));
    }

    public InvasionOfArcavios(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{U}{U}",
                "Invocation of the Founders",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "U"
        );

        // Invasion of Arcavios
        this.getLeftHalfCard().setStartingDefense(7);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Arcavios enters the battlefield, search your library, graveyard, and/or outside the game for an instant or sorcery card you own, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new InvasionOfArcaviosEffect()));

        // Invocation of the Founders
        // Whenever you cast an instant or sorcery spell from your hand, you may copy that spell. You may choose new targets for the copy.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(
                new InvocationOfTheFoundersEffect(), filter, true
        ));
    }

    private InvasionOfArcavios(final InvasionOfArcavios card) {
        super(card);
    }

    @Override
    public InvasionOfArcavios copy() {
        return new InvasionOfArcavios(this);
    }
}

class InvasionOfArcaviosEffect extends OneShotEffect {

    InvasionOfArcaviosEffect() {
        super(Outcome.Benefit);
        staticText = "search your library, graveyard, and/or outside the game for an instant or sorcery card you own, " +
                "reveal it, and put it into your hand. If you search your library this way, shuffle";
    }

    private InvasionOfArcaviosEffect(final InvasionOfArcaviosEffect effect) {
        super(effect);
    }

    @Override
    public InvasionOfArcaviosEffect copy() {
        return new InvasionOfArcaviosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(outcome, "Look outside the game?", source, game)
                && new WishEffect(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY).apply(game, source)) {
            return true;
        }
        return new SearchLibraryGraveyardPutInHandEffect(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, false
        ).apply(game, source);
    }
}

class InvocationOfTheFoundersEffect extends OneShotEffect {

    InvocationOfTheFoundersEffect() {
        super(Outcome.Benefit);
        staticText = "copy that spell. You may choose new targets for the copy";
    }

    private InvocationOfTheFoundersEffect(final InvocationOfTheFoundersEffect effect) {
        super(effect);
    }

    @Override
    public InvocationOfTheFoundersEffect copy() {
        return new InvocationOfTheFoundersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true);
            return true;
        }
        return false;
    }
}
