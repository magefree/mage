package mage.cards.g;

import mage.abilities.common.*;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class GwenStacy extends ModalDoubleFacedCard {

    public GwenStacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PERFORMER, SubType.HERO}, "{1}{R}",
                "Ghost-Spider",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIDER, SubType.HUMAN, SubType.HERO}, "{2}{U}{R}{W}");

        this.getLeftHalfCard().setPT(2, 1);
        this.getRightHalfCard().setPT(4, 4);

        // When Gwen Stacy enters, exile the top card of your library. You may play that card for as long as you control this creature.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.WhileControlled)
                .withTextOptions("that card", true)));
        // {2}{U}{R}{W}: Transform Gwen Stacy. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{2}{U}{R}{W}")));

        // Ghost-Spider
        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Whenever you play a land from exile or cast a spell from exile, put a +1/+1 counter on Ghost-Spider.
        this.getRightHalfCard().addAbility(new PlayLandOrCastSpellTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true, false));
        // Remove two counters from Ghost-Spider: Exile the top card of your library. You may play that card this turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                new RemoveCountersSourceCost(2)));
    }

    private GwenStacy(final GwenStacy card) {
        super(card);
    }

    @Override
    public GwenStacy copy() {
        return new GwenStacy(this);
    }
}
