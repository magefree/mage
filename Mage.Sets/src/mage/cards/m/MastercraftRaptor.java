package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MastercraftRaptor extends CardImpl {

    public MastercraftRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setRed(true);

        // Mastercraft Raptor's power is equal to the total power of the exiled cards used to craft it.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(MastercraftRaptorValue.instance)
                .setText("{this}'s power is equal to the total power of the exiled cards used to craft it")
        ));
    }

    private MastercraftRaptor(final MastercraftRaptor card) {
        super(card);
    }

    @Override
    public MastercraftRaptor copy() {
        return new MastercraftRaptor(this);
    }
}

enum MastercraftRaptorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(
                        game, sourceAbility.getSourceId(),
                        game.getState().getZoneChangeCounter(sourceAbility.getSourceId()) - 2
                ));
        if (exileZone == null) {
            return 0;
        }
        return exileZone
                .getCards(game)
                .stream()
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
    }

    @Override
    public MastercraftRaptorValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
