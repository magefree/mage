package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class KitesailApprentice extends CardImpl {

    public KitesailApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                EquippedSourceCondition.instance, "as long as {this} is equipped, it gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance()), EquippedSourceCondition.instance, "and has flying"
        ));
        this.addAbility(ability);
    }

    private KitesailApprentice(final KitesailApprentice card) {
        super(card);
    }

    @Override
    public KitesailApprentice copy() {
        return new KitesailApprentice(this);
    }
}
