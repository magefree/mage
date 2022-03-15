package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ArgivianArchaeologist extends CardImpl {

    public ArgivianArchaeologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {W}{W}, {tap}: Return target artifact card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl("{W}{W}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private ArgivianArchaeologist(final ArgivianArchaeologist card) {
        super(card);
    }

    @Override
    public ArgivianArchaeologist copy() {
        return new ArgivianArchaeologist(this);
    }
}
