package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.MapToken;

/**
 * @author
 */
public final class StormFleetNegotiator extends CardImpl {

    public StormFleetNegotiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Parley — Whenever Storm Fleet Negotiator attacks, each player reveals the top card of their library. For each nonland card revealed this way, you create a Map token. Then each player draws a card.
        Ability ability = new AttacksTriggeredAbility(new CreateTokenEffect(
                new MapToken(), ParleyCount.getInstance(), true, false
        ).setText("each player reveals the top card of their library. " +
                "For each nonland card revealed this way, you create a Map token"));
        ability.addEffect(new DrawCardAllEffect(1).concatBy("Then"));
        this.addAbility(ability.setAbilityWord(AbilityWord.PARLEY));
    }

    private StormFleetNegotiator(final StormFleetNegotiator card) {
        super(card);
    }

    @Override
    public StormFleetNegotiator copy() {
        return new StormFleetNegotiator(this);
    }
}
