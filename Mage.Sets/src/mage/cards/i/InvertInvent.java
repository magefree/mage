package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvertInvent extends SplitCard {

    public InvertInvent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/R}", "{4}{U}{R}", SpellAbilityType.SPLIT);

        // Invert
        // Switch the power and toughness of each of up to two target creatures until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new InvertEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Invent
        // Search your library for an instant card and/or a sorcery card, reveal them, put them into your hand, then shuffle your library.
        this.getRightHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new InventTarget(), true).setText("search your library for an instant card and/or a sorcery card, reveal them, put them into your hand, then shuffle"));
    }

    private InvertInvent(final InvertInvent card) {
        super(card);
    }

    @Override
    public InvertInvent copy() {
        return new InvertInvent(this);
    }
}

class InvertEffect extends OneShotEffect {

    InvertEffect() {
        super(Outcome.Benefit);
        this.staticText = "Switch the power and toughness of "
                + "each of up to two target creatures until end of turn.";
    }

    private InvertEffect(final InvertEffect effect) {
        super(effect);
    }

    @Override
    public InvertEffect copy() {
        return new InvertEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            ContinuousEffect effect = new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetId, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class InventTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterInstantOrSorceryCard("an instant card and/or a sorcery card");
    private static final CardTypeAssignment cardTypeAssigner
            = new CardTypeAssignment(CardType.INSTANT, CardType.SORCERY);

    InventTarget() {
        super(0, 2, filter);
    }

    private InventTarget(final InventTarget target) {
        super(target);
    }

    @Override
    public InventTarget copy() {
        return new InventTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
