package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScholarOfStars extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledArtifactPermanent("you control an artifact"));

    public ScholarOfStars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Scholar of Stars enters the battlefield, if you control an artifact, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(condition).addHint(ArtifactYouControlHint.instance));
    }

    private ScholarOfStars(final ScholarOfStars card) {
        super(card);
    }

    @Override
    public ScholarOfStars copy() {
        return new ScholarOfStars(this);
    }
}
