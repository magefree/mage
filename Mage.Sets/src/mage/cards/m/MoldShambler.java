package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class MoldShambler extends CardImpl {

    public MoldShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Kicker {1}{G} (You may pay an additional {1}{G} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{G}"));

        // When Mold Shambler enters the battlefield, if it was kicked, destroy target noncreature permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect()).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE));
        this.addAbility(ability);
    }

    private MoldShambler(final MoldShambler card) {
        super(card);
    }

    @Override
    public MoldShambler copy() {
        return new MoldShambler(this);
    }
}
