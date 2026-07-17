package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TamObservantSequencer extends PrepareCard {

    public TamObservantSequencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}", "Deep Sight", new CardType[]{CardType.SORCERY}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, Tam becomes prepared.
        this.addAbility(new LandfallAbility(new BecomePreparedSourceEffect()));

        // Deep Sight
        // Sorcery {G}{U}
        // You draw a card and gain 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(StaticValue.get(1), "and gain 1 life"));
    }

    private TamObservantSequencer(final TamObservantSequencer card) {
        super(card);
    }

    @Override
    public TamObservantSequencer copy() {
        return new TamObservantSequencer(this);
    }
}
