
package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author mluds
 */
public final class ParasiticStrix extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("black permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ParasiticStrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Parasitic Strix enters the battlefield, if you control a black permanent, target player loses 2 life and you gain 2 life.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.withInterveningIf(new YouControlPermanentCondition(filter));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ParasiticStrix(final ParasiticStrix card) {
        super(card);
    }

    @Override
    public ParasiticStrix copy() {
        return new ParasiticStrix(this);
    }
}
