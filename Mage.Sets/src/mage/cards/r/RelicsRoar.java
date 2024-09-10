package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicsRoar extends CardImpl {

    public RelicsRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Until end of turn, target artifact or creature becomes a Dinosaur artifact creature with base power and toughness 4/3 in addition to its other types.
        this.getSpellAbility().addEffect(new RelicsRoarEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private RelicsRoar(final RelicsRoar card) {
        super(card);
    }

    @Override
    public RelicsRoar copy() {
        return new RelicsRoar(this);
    }
}

class RelicsRoarEffect extends ContinuousEffectImpl {

    RelicsRoarEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target artifact or creature becomes a Dinosaur artifact creature " +
                "with base power and toughness 4/3 in addition to its other types";
    }

    private RelicsRoarEffect(final RelicsRoarEffect effect) {
        super(effect);
    }

    @Override
    public RelicsRoarEffect copy() {
        return new RelicsRoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
                permanent.addSubType(game, SubType.DINOSAUR);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(4);
                    permanent.getToughness().setModifiedBaseValue(3);
                    return true;
                }
            default:
                return false;
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case PTChangingEffects_7:
                return true;
            default:
                return false;
        }
    }
}
