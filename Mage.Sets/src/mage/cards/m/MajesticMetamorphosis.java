package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class MajesticMetamorphosis extends CardImpl {

    public MajesticMetamorphosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Until end of turn, target artifact or creature becomes a 4/4 Angel artifact creature and gains flying.
        this.getSpellAbility().addEffect(new MajesticMetamorphosisEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private MajesticMetamorphosis(final MajesticMetamorphosis card) {
        super(card);
    }

    @Override
    public MajesticMetamorphosis copy() {
        return new MajesticMetamorphosis(this);
    }
}

class MajesticMetamorphosisEffect extends ContinuousEffectImpl {

    MajesticMetamorphosisEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target artifact or creature becomes " +
                "a 4/4 Angel artifact creature and gains flying";
    }

    private MajesticMetamorphosisEffect(final MajesticMetamorphosisEffect effect) {
        super(effect);
    }

    @Override
    public MajesticMetamorphosisEffect copy() {
        return new MajesticMetamorphosisEffect(this);
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
                permanent.removeAllCardTypes(game);
                permanent.addCardType(game, CardType.ARTIFACT, CardType.CREATURE);
                permanent.removeAllSubTypes(game);
                permanent.addSubType(game, SubType.ANGEL);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(4);
                    permanent.getToughness().setModifiedBaseValue(4);
                    return true;
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
