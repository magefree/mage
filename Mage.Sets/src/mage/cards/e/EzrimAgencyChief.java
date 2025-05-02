package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EzrimAgencyChief extends CardImpl {

    public EzrimAgencyChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ARCHON);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ezrim, Agency Chief enters the battlefield, investigate twice.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(2)));

        // {1}, Sacrifice an artifact: Ezrim gains your choice of vigilance, lifelink, or hexproof until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                VigilanceAbility.getInstance(), LifelinkAbility.getInstance(), HexproofAbility.getInstance()),
                new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private EzrimAgencyChief(final EzrimAgencyChief card) {
        super(card);
    }

    @Override
    public EzrimAgencyChief copy() {
        return new EzrimAgencyChief(this);
    }
}
