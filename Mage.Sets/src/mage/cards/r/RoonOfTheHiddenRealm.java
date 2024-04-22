package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RoonOfTheHiddenRealm extends CardImpl {

    public RoonOfTheHiddenRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {2}, {tap}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileReturnBattlefieldNextEndStepTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private RoonOfTheHiddenRealm(final RoonOfTheHiddenRealm card) {
        super(card);
    }

    @Override
    public RoonOfTheHiddenRealm copy() {
        return new RoonOfTheHiddenRealm(this);
    }
}
