package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AnchovyAndBananaPizza extends CardImpl {

    public AnchovyAndBananaPizza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        this.subtype.add(SubType.FOOD);

        // When this artifact enters, destroy target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this artifact: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private AnchovyAndBananaPizza(final AnchovyAndBananaPizza card) {
        super(card);
    }

    @Override
    public AnchovyAndBananaPizza copy() {
        return new AnchovyAndBananaPizza(this);
    }
}
