package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MordenkainensPolymorph extends CardImpl {

    public MordenkainensPolymorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, target creature becomes a Dragon with base power and toughness 4/4 and gains flying.
        this.getSpellAbility().addEffect(new MordenkainensPolymorphEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MordenkainensPolymorph(final MordenkainensPolymorph card) {
        super(card);
    }

    @Override
    public MordenkainensPolymorph copy() {
        return new MordenkainensPolymorph(this);
    }
}

class MordenkainensPolymorphEffect extends ContinuousEffectImpl {

    MordenkainensPolymorphEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target creature becomes " +
                "a Dragon with base power and toughness 4/4 and gains flying";
    }

    private MordenkainensPolymorphEffect(final MordenkainensPolymorphEffect effect) {
        super(effect);
    }

    @Override
    public MordenkainensPolymorphEffect copy() {
        return new MordenkainensPolymorphEffect(this);
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
                permanent.removeAllCreatureTypes(game);
                permanent.addSubType(game, SubType.DRAGON);
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
