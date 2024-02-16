package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitesailCleric extends CardImpl {

    public KitesailCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {2}{W}
        this.addAbility(new KickerAbility("{2}{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kitesail Cleric enters the battelfield, if it was kicked, tap up to two target creatures.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new TapTargetEffect()),
                KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, tap up to two target creatures."
        );
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private KitesailCleric(final KitesailCleric card) {
        super(card);
    }

    @Override
    public KitesailCleric copy() {
        return new KitesailCleric(this);
    }
}
