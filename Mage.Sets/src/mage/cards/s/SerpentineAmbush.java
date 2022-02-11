package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
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
public final class SerpentineAmbush extends CardImpl {

    public SerpentineAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, target creature becomes a blue Serpent with base power and toughness 5/5.
        this.getSpellAbility().addEffect(new SerpentineAmbushEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SerpentineAmbush(final SerpentineAmbush card) {
        super(card);
    }

    @Override
    public SerpentineAmbush copy() {
        return new SerpentineAmbush(this);
    }
}

class SerpentineAmbushEffect extends ContinuousEffectImpl {

    SerpentineAmbushEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target creature becomes " +
                "a blue Serpent with base power and toughness 5/5";
    }

    private SerpentineAmbushEffect(final SerpentineAmbushEffect effect) {
        super(effect);
    }

    @Override
    public SerpentineAmbushEffect copy() {
        return new SerpentineAmbushEffect(this);
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
                permanent.addSubType(game, SubType.SERPENT);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setColor(ObjectColor.BLUE);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setValue(5);
                    permanent.getToughness().setValue(5);
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
            case ColorChangingEffects_5:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
