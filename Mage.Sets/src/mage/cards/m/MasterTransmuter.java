
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class MasterTransmuter extends CardImpl {

    public MasterTransmuter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}, {tap}, Return an artifact you control to its owner's hand: You may put an artifact card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(new FilterArtifactCard("an artifact card")), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact"))));
        this.addAbility(ability);

    }

    private MasterTransmuter(final MasterTransmuter card) {
        super(card);
    }

    @Override
    public MasterTransmuter copy() {
        return new MasterTransmuter(this);
    }
}
