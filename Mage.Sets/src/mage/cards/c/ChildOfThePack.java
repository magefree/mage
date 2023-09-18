package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChildOfThePack extends CardImpl {

    public ChildOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.s.SavagePackmate.class;

        // {2}{R}{G}: Create a 2/2 green Wolf creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new WolfToken()), new ManaCostsImpl<>("{2}{R}{G}")
        ));

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private ChildOfThePack(final ChildOfThePack card) {
        super(card);
    }

    @Override
    public ChildOfThePack copy() {
        return new ChildOfThePack(this);
    }
}
