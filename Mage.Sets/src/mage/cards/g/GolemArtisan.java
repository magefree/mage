
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE;

/**
 * @author nantuko
 */
public final class GolemArtisan extends CardImpl {

    public GolemArtisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target artifact creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(FILTER_PERMANENT_ARTIFACT_CREATURE));
        this.addAbility(ability);

        // {2}: Target artifact creature gains your choice of flying, trample, or haste until end of turn.
        ability = new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(
                FlyingAbility.getInstance(), TrampleAbility.getInstance(), HasteAbility.getInstance()), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(FILTER_PERMANENT_ARTIFACT_CREATURE));
        this.addAbility(ability);

    }

    private GolemArtisan(final GolemArtisan card) {
        super(card);
    }

    @Override
    public GolemArtisan copy() {
        return new GolemArtisan(this);
    }
}
