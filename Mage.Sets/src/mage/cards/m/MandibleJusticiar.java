package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MandibleJusticiar extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MandibleJusticiar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another artifact enters the battlefield under your control, Mandible Justiciar gets +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter
        ));
    }

    private MandibleJusticiar(final MandibleJusticiar card) {
        super(card);
    }

    @Override
    public MandibleJusticiar copy() {
        return new MandibleJusticiar(this);
    }
}
