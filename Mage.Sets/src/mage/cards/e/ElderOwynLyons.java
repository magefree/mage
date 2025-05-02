package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class ElderOwynLyons extends CardImpl {

    public ElderOwynLyons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Artifacts you control have ward {1}.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(
                        new WardAbility(new GenericManaCost(1)), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS
                )
        ));

        // When Elder Owyn Lyons enters the battlefield or dies, return target artifact card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private ElderOwynLyons(final ElderOwynLyons card) {
        super(card);
    }

    @Override
    public ElderOwynLyons copy() {
        return new ElderOwynLyons(this);
    }
}
