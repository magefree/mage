package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScrapworkCohort extends CardImpl {

    public ScrapworkCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Scrapwork Cohort enters the battlefield, create a 1/1 colorless Soldier artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierArtifactToken())));

        // Unearth {2}{W}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{W}")));
    }

    private ScrapworkCohort(final ScrapworkCohort card) {
        super(card);
    }

    @Override
    public ScrapworkCohort copy() {
        return new ScrapworkCohort(this);
    }
}
