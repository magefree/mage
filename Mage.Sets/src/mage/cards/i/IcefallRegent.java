package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IcefallRegent extends CardImpl {

    public IcefallRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Icefall Regent enters the battlefield, tap target creature an opponent controls.
        // That creature doesn't untap during its controller's untap step for as long as you control Icefall Regent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DontUntapInControllersUntapStepTargetEffect(Duration.WhileControlled));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);

        // Spells your opponents cast that target Icefall Regent cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostModificationThatTargetSourceEffect(
                2, new FilterCard("Spells"), TargetController.OPPONENT
        )));
    }

    private IcefallRegent(final IcefallRegent card) {
        super(card);
    }

    @Override
    public IcefallRegent copy() {
        return new IcefallRegent(this);
    }
}
