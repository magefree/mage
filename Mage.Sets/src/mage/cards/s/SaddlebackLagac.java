
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SaddlebackLagac extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("target creatures");

    static {
        FILTER.add(AnotherPredicate.instance);
    }

    public SaddlebackLagac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Saddleback Lagac enters the battlefield, support 2.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SupportEffect(this, 2, true), false);
        ability.addTarget(new TargetCreaturePermanent(0, 2, FILTER, false));
        this.addAbility(ability);

    }

    private SaddlebackLagac(final SaddlebackLagac card) {
        super(card);
    }

    @Override
    public SaddlebackLagac copy() {
        return new SaddlebackLagac(this);
    }
}
