package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutthroatNegotiator extends CardImpl {

    public CutthroatNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Parley -- Whenever Cutthroat Negotiator attacks, each player reveals the top card of their library. For each nonland card revealed this way, you create a tapped Treasure token. Then each player draws a card.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(
                new TreasureToken(), ParleyCount.getInstance(), true, false
        ).setText("each player reveals the top card of their library. " +
                "For each nonland card revealed this way, you create a tapped Treasure token"));
        ability.addEffect(new DrawCardAllEffect(1).concatBy("Then"));
        this.addAbility(ability.setAbilityWord(AbilityWord.PARLEY));
    }

    private CutthroatNegotiator(final CutthroatNegotiator card) {
        super(card);
    }

    @Override
    public CutthroatNegotiator copy() {
        return new CutthroatNegotiator(this);
    }
}
