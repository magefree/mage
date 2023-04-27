package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmenOfTheDead extends CardImpl {

    public OmenOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Omen of the Dead enters the battlefield, return target creature card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // {2}{B}, Sacrifice Omen of the Dead: Scry 2.
        ability = new SimpleActivatedAbility(new ScryEffect(2), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private OmenOfTheDead(final OmenOfTheDead card) {
        super(card);
    }

    @Override
    public OmenOfTheDead copy() {
        return new OmenOfTheDead(this);
    }
}
