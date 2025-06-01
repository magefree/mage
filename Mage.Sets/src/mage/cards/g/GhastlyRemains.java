package mage.cards.g;

import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class GhastlyRemains extends CardImpl {

    public GhastlyRemains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));

        // At the beginning of your upkeep, if Ghastly Remains is in your graveyard, you may pay {B}{B}{B}. If you do, return Ghastly Remains to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                TargetController.YOU, new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect().setText("return it to your hand"), new ManaCostsImpl<>("{B}{B}{B}")),
                false).withInterveningIf(SourceInGraveyardCondition.instance));

    }

    private GhastlyRemains(final GhastlyRemains card) {
        super(card);
    }

    @Override
    public GhastlyRemains copy() {
        return new GhastlyRemains(this);
    }
}
