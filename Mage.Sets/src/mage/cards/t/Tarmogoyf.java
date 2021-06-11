package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Tarmogoyf extends CardImpl {

    public Tarmogoyf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.LHURGOYF);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TarmogoyfEffect()).addHint(CardTypesInGraveyardHint.ALL));
    }

    private Tarmogoyf(final Tarmogoyf card) {
        super(card);
    }

    @Override
    public Tarmogoyf copy() {
        return new Tarmogoyf(this);
    }
}

class TarmogoyfEffect extends ContinuousEffectImpl {

    public TarmogoyfEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1";
    }

    public TarmogoyfEffect(final TarmogoyfEffect effect) {
        super(effect);
    }

    @Override
    public TarmogoyfEffect copy() {
        return new TarmogoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject target = source.getSourceObject(game);
        if (target == null) {
            return false;
        }
        int number = CardTypesInGraveyardCount.ALL.calculate(game, source, this);
        target.getPower().setValue(number);
        target.getToughness().setValue(number + 1);
        return true;
    }
}
