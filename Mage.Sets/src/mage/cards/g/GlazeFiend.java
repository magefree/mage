
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Plopman
 */
public final class GlazeFiend extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("another artifact");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    
    public GlazeFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever another artifact enters the battlefield under your control, Glaze Fiend gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), filter));
    }

    private GlazeFiend(final GlazeFiend card) {
        super(card);
    }

    @Override
    public GlazeFiend copy() {
        return new GlazeFiend(this);
    }
}
