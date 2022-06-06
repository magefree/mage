
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class NephaliaMoondrakes extends CardImpl {

    public NephaliaMoondrakes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nephalia Moondrakes enters the battlefield, target creature gains flying until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {4}{U}{U}, Exile Nephalia Moondrakes from your graveyard: Creatures you control gain flying until end of turn.
        ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, FILTER_PERMANENT_CREATURES), new ManaCostsImpl<>("{4}{U}{U}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private NephaliaMoondrakes(final NephaliaMoondrakes card) {
        super(card);
    }

    @Override
    public NephaliaMoondrakes copy() {
        return new NephaliaMoondrakes(this);
    }
}
