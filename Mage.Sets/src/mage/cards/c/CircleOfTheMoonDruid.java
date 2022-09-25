package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CircleOfTheMoonDruid extends CardImpl {

    public CircleOfTheMoonDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Bear Form — As long as its your turn, Circle of the Moon Druid is a Bear with base power and toughness 4/2.
        this.addAbility(new SimpleStaticAbility(new CircleOfTheMoonDruidBearEffect()).withFlavorWord("Bear Form"));
    }

    private CircleOfTheMoonDruid(final CircleOfTheMoonDruid card) {
        super(card);
    }

    @Override
    public CircleOfTheMoonDruid copy() {
        return new CircleOfTheMoonDruid(this);
    }
}

class CircleOfTheMoonDruidBearEffect extends ContinuousEffectImpl {

    CircleOfTheMoonDruidBearEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "as long as it's your turn, {this} is a Bear with base power and toughness 4/2";
    }

    private CircleOfTheMoonDruidBearEffect(final CircleOfTheMoonDruidBearEffect effect) {
        super(effect);
    }

    @Override
    public CircleOfTheMoonDruidBearEffect copy() {
        return new CircleOfTheMoonDruidBearEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCreatureTypes(game);
                permanent.addSubType(game, SubType.BEAR);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(4);
                    permanent.getToughness().setModifiedBaseValue(2);
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
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.PTChangingEffects_7;
    }
}
