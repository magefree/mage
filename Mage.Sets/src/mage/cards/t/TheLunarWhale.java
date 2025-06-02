package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttackedThisTurnSourceCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheLunarWhale extends CardImpl {

    public TheLunarWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // As long as The Lunar Whale attacked this turn, you may play the top card of your library.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new PlayFromTopOfLibraryEffect(), AttackedThisTurnSourceCondition.instance
        ).setText("as long as {this} attacked this turn, you may play the top card of your library")));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private TheLunarWhale(final TheLunarWhale card) {
        super(card);
    }

    @Override
    public TheLunarWhale copy() {
        return new TheLunarWhale(this);
    }
}
