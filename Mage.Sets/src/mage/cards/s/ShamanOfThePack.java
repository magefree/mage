
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class ShamanOfThePack extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("equal to the number of elves you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public ShamanOfThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Shaman of the Pack enters the battlefield, target opponent loses life equal to the number of Elves you control.
        Effect effect = new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter));
        effect.setText("target opponent loses life equal to the number of Elves you control");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public ShamanOfThePack(final ShamanOfThePack card) {
        super(card);
    }

    @Override
    public ShamanOfThePack copy() {
        return new ShamanOfThePack(this);
    }
}
