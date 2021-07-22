package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlyInstigator extends CardImpl {

    public SlyInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {U}, {T}: Until your next turn, target creature an opponent controls can't be blocked. Goad that creature.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedTargetEffect(Duration.UntilYourNextTurn)
                        .setText("until your next turn, target creature an opponent controls can't be blocked."),
                new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GoadTargetEffect().setText("Goad that creature"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private SlyInstigator(final SlyInstigator card) {
        super(card);
    }

    @Override
    public SlyInstigator copy() {
        return new SlyInstigator(this);
    }
}
