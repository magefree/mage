package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidgarCityOfMako extends AdventureCard {

    public MidgarCityOfMako(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, new CardType[]{CardType.SORCERY}, "", "Reactor Raid", "{2}{B}");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Reactor Raid
        // You may sacrifice an artifact or creature. If you do, draw two cards.
        this.getSpellCard().getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(2),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
        ));
        this.finalizeAdventure();
    }

    private MidgarCityOfMako(final MidgarCityOfMako card) {
        super(card);
    }

    @Override
    public MidgarCityOfMako copy() {
        return new MidgarCityOfMako(this);
    }
}
