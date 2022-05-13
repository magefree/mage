package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryAndExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class RootwaterThief extends CardImpl {

    public RootwaterThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}: Rootwater Thief gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{U}")));

        // Whenever Rootwater Thief deals combat damage to a player, you may pay {2}. If you do, search that player's library for a card and exile it, then the player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new SearchLibraryAndExileTargetEffect(1, false), new GenericManaCost(2)
        ), false, true));
    }

    private RootwaterThief(final RootwaterThief card) {
        super(card);
    }

    @Override
    public RootwaterThief copy() {
        return new RootwaterThief(this);
    }
}
