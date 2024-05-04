package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.MorphManacostVariableValue;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.target.targetadjustment.TargetsCountAdjuster;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AureliasVindicator extends CardImpl {

    public AureliasVindicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Disguise {X}{3}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{X}{3}{W}")));

        // When Aurelia's Vindicator is turned face up, exile up to X other target creatures from the battlefield and/or creature cards from graveyards.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new ExileTargetForSourceEffect()
                .setText("exile up to X other target creatures from the battlefield and/or creature cards from graveyards"));
        ability.setTargetAdjuster(new TargetsCountAdjuster(MorphManacostVariableValue.instance));
        ability.addTarget(new TargetCardInGraveyardBattlefieldOrStack(
                0, 1, StaticFilters.FILTER_CARD_CREATURE, StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.addAbility(ability);

        // When Aurelia's Vindicator leaves the battlefield, return the exiled cards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.HAND)
                .withText(true, true, false), false));
    }

    private AureliasVindicator(final AureliasVindicator card) {
        super(card);
    }

    @Override
    public AureliasVindicator copy() {
        return new AureliasVindicator(this);
    }
}
