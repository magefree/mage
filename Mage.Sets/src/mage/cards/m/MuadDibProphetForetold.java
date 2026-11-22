package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class MuadDibProphetForetold extends CardImpl {

    public MuadDibProphetForetold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // Landfall -- Whenever a land you control enters, you may mill a card.
        this.addAbility(new LandfallAbility(new MillCardsControllerEffect(1), true));

        // Delirium -- As long as there are four or more card types among cards in your graveyard, you may play the top card of your library.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
            new PlayFromTopOfLibraryEffect(), DeliriumCondition.instance
        ).setText("as long as there are four or more card types among cards in your graveyard, you may play the top card of your library"))
            .setAbilityWord(AbilityWord.DELIRIUM)
            .addHint(CardTypesInGraveyardCount.YOU.getHint()
        ));
    }

    private MuadDibProphetForetold(final MuadDibProphetForetold card) {
        super(card);
    }

    @Override
    public MuadDibProphetForetold copy() {
        return new MuadDibProphetForetold(this);
    }
}
