package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HopeEnderCoatl extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public HopeEnderCoatl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When you cast this spell, counter target spell an opponent controls unless they pay {1}.
        Ability ability = new CastSourceTriggeredAbility(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        ability.addTarget(new TargetSpell(filter));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private HopeEnderCoatl(final HopeEnderCoatl card) {
        super(card);
    }

    @Override
    public HopeEnderCoatl copy() {
        return new HopeEnderCoatl(this);
    }
}
