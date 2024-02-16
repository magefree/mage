package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class SigilOfValor extends CardImpl {

    public SigilOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks alone, it gets +1/+1 until end of turn for each other creature you control.
        this.addAbility(new AttacksAloneAttachedTriggeredAbility(
                new BoostTargetEffect(SigilOfValorCount.instance, SigilOfValorCount.instance, Duration.EndOfTurn),
                AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private SigilOfValor(final SigilOfValor card) {
        super(card);
    }

    @Override
    public SigilOfValor copy() {
        return new SigilOfValor(this);
    }
}

enum SigilOfValorCount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        UUID attackerId = effect.getTargetPointer().getFirst(game, sourceAbility);
        if (attackerId != null) {
            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
            filter.add(Predicates.not(new CardIdPredicate(attackerId)));
            return game.getBattlefield().count(filter, sourceAbility.getControllerId(), sourceAbility, game);
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "other creature you control";
    }
}
