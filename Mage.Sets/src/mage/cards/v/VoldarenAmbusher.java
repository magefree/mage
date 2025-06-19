package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoldarenAmbusher extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(
            new FilterControlledPermanent(SubType.VAMPIRE, "Vampires you control"), null
    );
    private static final Hint hint = new ValueHint("Vampires you control", xValue);

    public VoldarenAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Voldaren Ambusher enters the battlefield, if an opponent lost life this turn, it deals X damage to up to one target creature or planeswalker, where X is the number of Vampires you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue, "it"))
                .withInterveningIf(OpponentsLostLifeCondition.instance);
        ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1));
        this.addAbility(ability.addHint(OpponentsLostLifeHint.instance).addHint(hint));
    }

    private VoldarenAmbusher(final VoldarenAmbusher card) {
        super(card);
    }

    @Override
    public VoldarenAmbusher copy() {
        return new VoldarenAmbusher(this);
    }
}
