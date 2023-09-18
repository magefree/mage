package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaPrinceOfKroog extends CardImpl {

    public UrzaPrinceOfKroog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Artifact creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // {6}: Create a token that's a copy of target artifact you control, except it's a 1/1 Soldier creature in addition to its other types.
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 1, false,
                false, null, 1, 1, false
        ).setAdditionalSubType(SubType.SOLDIER).setText("create a token that's a copy of target artifact you control, " +
                "except it's a 1/1 Soldier creature in addition to its other types"), new GenericManaCost(6));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);
    }

    private UrzaPrinceOfKroog(final UrzaPrinceOfKroog card) {
        super(card);
    }

    @Override
    public UrzaPrinceOfKroog copy() {
        return new UrzaPrinceOfKroog(this);
    }
}
