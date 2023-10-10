package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LaurineTheDiversion extends CardImpl {

    public LaurineTheDiversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Kamber, the Plunderer
        this.addAbility(new PartnerWithAbility("Kamber, the Plunderer"));

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // {2}, Sacrifice an artifact or creature: Goad target creature.
        Ability ability = new SimpleActivatedAbility(new GoadTargetEffect(), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LaurineTheDiversion(final LaurineTheDiversion card) {
        super(card);
    }

    @Override
    public LaurineTheDiversion copy() {
        return new LaurineTheDiversion(this);
    }
}
