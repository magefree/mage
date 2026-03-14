package mage.cards.g;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GuacAndMarshmallowPizza extends CardImpl {

    public GuacAndMarshmallowPizza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.FOOD);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this artifact enters, target creature gets +2/+2 until end of turn. Untap it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2));
        ability.addEffect(new UntapTargetEffect().setText("untap it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private GuacAndMarshmallowPizza(final GuacAndMarshmallowPizza card) {
        super(card);
    }

    @Override
    public GuacAndMarshmallowPizza copy() {
        return new GuacAndMarshmallowPizza(this);
    }
}
