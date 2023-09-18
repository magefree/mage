package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class CobbledLancer extends CardImpl {

    public CobbledLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));

        // {3}{U}, Exile Cobbled Lancer from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private CobbledLancer(final CobbledLancer card) {
        super(card);
    }

    @Override
    public CobbledLancer copy() {
        return new CobbledLancer(this);
    }
}
