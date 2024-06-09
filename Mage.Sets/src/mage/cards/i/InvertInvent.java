package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.target.common.TargetCardAndOrCardInLibrary;
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
        this.getRightHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardAndOrCardInLibrary(CardType.INSTANT, CardType.SORCERY), true));
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
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            ContinuousEffect effect = new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetId, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}
