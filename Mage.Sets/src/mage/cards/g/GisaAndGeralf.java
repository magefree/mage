package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.CastFromGraveyardOnceEachTurnAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class GisaAndGeralf extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a Zombie creature spell");
    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GisaAndGeralf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Gisa and Geralf enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4)));

        // Once during each of your turns, you may cast a Zombie creature spell from your graveyard
        this.addAbility(new CastFromGraveyardOnceEachTurnAbility(filter));
    }

    private GisaAndGeralf(final GisaAndGeralf card) {
        super(card);
    }

    @Override
    public GisaAndGeralf copy() {
        return new GisaAndGeralf(this);
    }
}
