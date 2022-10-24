package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldbugHumanitysAlly extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.HUMAN, "attacking Humans you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public GoldbugHumanitysAlly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.g.GoldbugScrappyScout.class;

        // More Than Meets the Eye {W}{U}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{W}{U}"));

        // Prevent all combat damage that would be dealt to attacking Humans you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToAllEffect(
                Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever you cast your second spell each turn, convert Goldbug.
        this.addAbility(new CastSecondSpellTriggeredAbility(new TransformSourceEffect().setText("convert {this}")));
    }

    private GoldbugHumanitysAlly(final GoldbugHumanitysAlly card) {
        super(card);
    }

    @Override
    public GoldbugHumanitysAlly copy() {
        return new GoldbugHumanitysAlly(this);
    }
}
