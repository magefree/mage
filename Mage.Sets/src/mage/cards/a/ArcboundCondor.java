package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcboundCondor extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("another artifacr");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ArcboundCondor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));

        // Whenever another artifact enters the battlefield under your control, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoostTargetEffect(-1, -1), filter);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ArcboundCondor(final ArcboundCondor card) {
        super(card);
    }

    @Override
    public ArcboundCondor copy() {
        return new ArcboundCondor(this);
    }
}
