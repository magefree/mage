

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GoblinRuinblaster extends CardImpl {

    public GoblinRuinblaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kicker {R} (You may pay an additional {R} as you cast this spell.)
        this.addAbility(new KickerAbility("{R}"));


        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Goblin Ruinblaster enters the battlefield, if it was kicked, destroy target nonbasic land.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.ONCE, "When {this} enters the battlefield, if it was kicked, destroy target nonbasic land."));
    }

    private GoblinRuinblaster(final GoblinRuinblaster card) {
        super(card);
    }

    @Override
    public GoblinRuinblaster copy() {
        return new GoblinRuinblaster(this);
    }
}

