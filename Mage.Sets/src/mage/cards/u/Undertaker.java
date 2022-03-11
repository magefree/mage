package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author shieldal
 */
public final class Undertaker extends CardImpl {

    public Undertaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}, Discard a card: Return target creature card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private Undertaker(final Undertaker card) {
        super(card);
    }

    @Override
    public Undertaker copy() {
        return new Undertaker(this);
    }
}
