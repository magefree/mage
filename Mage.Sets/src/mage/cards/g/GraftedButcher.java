package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraftedButcher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PHYREXIAN, "Phyrexians");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent(SubType.PHYREXIAN, "Phyrexians");

    public GraftedButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Grafted Butcher enters the battlefield, Phyrexians you control gain menace until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new GainAbilityControlledEffect(new MenaceAbility(false), Duration.EndOfTurn, filter)
        ));

        // Other Phyrexians you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, true
        )));

        // {3}{B}, Sacrifice an artifact or creature: Return Grafted Butcher from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl<>("{3}{B}")
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ARTIFACT_OR_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private GraftedButcher(final GraftedButcher card) {
        super(card);
    }

    @Override
    public GraftedButcher copy() {
        return new GraftedButcher(this);
    }
}
