package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreamtailHeron extends CardImpl {

    public DreamtailHeron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Mutate {3}{U}
        this.addAbility(new MutateAbility(this, "{3}{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, draw a card.
        this.addAbility(new MutatesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private DreamtailHeron(final DreamtailHeron card) {
        super(card);
    }

    @Override
    public DreamtailHeron copy() {
        return new DreamtailHeron(this);
    }
}
