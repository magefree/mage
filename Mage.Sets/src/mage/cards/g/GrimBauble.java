package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimBauble extends CardImpl {

    public GrimBauble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        // When this artifact enters, target creature an opponent controls gets -2/-2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {2}{B}, {T}, Sacrifice this artifact: Surveil 2.
        ability = new SimpleActivatedAbility(new SurveilEffect(2), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GrimBauble(final GrimBauble card) {
        super(card);
    }

    @Override
    public GrimBauble copy() {
        return new GrimBauble(this);
    }
}
