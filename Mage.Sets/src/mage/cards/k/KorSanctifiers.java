
package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class KorSanctifiers extends CardImpl {

    public KorSanctifiers (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));

        // When Kor Sanctifiers enters the battlefield, if it was kicked, destroy target artifact or enchantment.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, destroy target artifact or enchantment."));
    }

    public KorSanctifiers (final KorSanctifiers card) {
        super(card);
    }

    @Override
    public KorSanctifiers copy() {
        return new KorSanctifiers(this);
    }
}
