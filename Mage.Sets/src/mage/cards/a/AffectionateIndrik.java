package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AffectionateIndrik extends CardImpl {

    public AffectionateIndrik(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Affectionate Indrik enters the battlefield, you may have it fight target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new FightTargetSourceEffect()
                        .setText("you may have it fight target creature you don't control. " +
                                "<i>(Each deals damage equal to its power to the other.)</i>"),
                true
        );
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private AffectionateIndrik(final AffectionateIndrik card) {
        super(card);
    }

    @Override
    public AffectionateIndrik copy() {
        return new AffectionateIndrik(this);
    }
}
