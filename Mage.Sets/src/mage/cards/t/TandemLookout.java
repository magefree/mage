
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityPairedEffect;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author noxx
 */
public final class TandemLookout extends CardImpl {

    private static final String ruleText = "As long as {this} is paired with another creature, each of those creatures has \"Whenever this creature deals damage to an opponent, draw a card.\"";

    public TandemLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Soulbond
        this.addAbility(new SoulbondAbility());

        // As long as Tandem Lookout is paired with another creature, each of those creatures has "Whenever this creature deals damage to an opponent, draw a card."
        Ability ability = new DealsDamageToOpponentTriggeredAbility(new DrawCardSourceControllerEffect(1));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityPairedEffect(ability, ruleText)));
    }

    private TandemLookout(final TandemLookout card) {
        super(card);
    }

    @Override
    public TandemLookout copy() {
        return new TandemLookout(this);
    }
}
