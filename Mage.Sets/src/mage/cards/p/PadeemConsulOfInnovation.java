
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ControlsPermanentGreatestCMCCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class PadeemConsulOfInnovation extends CardImpl {

    public PadeemConsulOfInnovation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Artifacts you control have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, new FilterArtifactPermanent(), false)));

        // At the beginning of your upkeep, if you control the artifact with the highest converted mana cost or tied for the highest converted mana cost, draw a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1), TargetController.YOU, false),
                new ControlsPermanentGreatestCMCCondition(new FilterArtifactPermanent()),
                "At the beginning of your upkeep, if you control the artifact with the highest converted mana cost or tied for the highest converted mana cost, draw a card.");

        this.addAbility(ability);
    }

    public PadeemConsulOfInnovation(final PadeemConsulOfInnovation card) {
        super(card);
    }

    @Override
    public PadeemConsulOfInnovation copy() {
        return new PadeemConsulOfInnovation(this);
    }
}
