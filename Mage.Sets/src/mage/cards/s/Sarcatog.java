
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class Sarcatog extends CardImpl {

    public Sarcatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Exile two cards from your graveyard: Sarcatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(2,new FilterCard("cards")))));

        // Sacrifice an artifact: Sarcatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("artifact")))));
    }

    private Sarcatog(final Sarcatog card) {
        super(card);
    }

    @Override
    public Sarcatog copy() {
        return new Sarcatog(this);
    }
}
