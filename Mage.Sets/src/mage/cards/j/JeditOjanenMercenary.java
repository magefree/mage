package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CatWarriorToken;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class JeditOjanenMercenary extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public JeditOjanenMercenary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.CAT);
        this.addSubType(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Jedit Ojanen, Mercenary or another legendary creature enters the battlefield under your control, you may pay {G}.
        // If you do, create a 2/2 green Cat Warrior creature token with forestwalk.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new CatWarriorToken()), new ManaCostsImpl<>("{G}")
        ), filter, false, true));
    }

    private JeditOjanenMercenary(final JeditOjanenMercenary card) {
        super(card);
    }

    @Override
    public JeditOjanenMercenary copy() {
        return new JeditOjanenMercenary(this);
    }
}
