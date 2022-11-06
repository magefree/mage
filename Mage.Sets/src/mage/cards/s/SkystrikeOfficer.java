package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.SoldierArtifactToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkystrikeOfficer extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.SOLDIER, "untapped Soldiers");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SkystrikeOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skystrike Officer attacks, create a 1/1 colorless Soldier artifact creature token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new SoldierArtifactToken())));

        // Tap three untapped Soldiers you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(3, filter))
        ));
    }

    private SkystrikeOfficer(final SkystrikeOfficer card) {
        super(card);
    }

    @Override
    public SkystrikeOfficer copy() {
        return new SkystrikeOfficer(this);
    }
}
