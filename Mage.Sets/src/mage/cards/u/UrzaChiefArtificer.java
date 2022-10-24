package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KarnConstructToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaChiefArtificer extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Artifact creatures you control", new PermanentsOnBattlefieldCount(filter)
    );

    public UrzaChiefArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Affinity for artifact creatures
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));

        // Artifact creatures you control have menace.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield, filter)
        ));

        // At the beginning of your end step, create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new KarnConstructToken()), TargetController.YOU, false
        ));
    }

    private UrzaChiefArtificer(final UrzaChiefArtificer card) {
        super(card);
    }

    @Override
    public UrzaChiefArtificer copy() {
        return new UrzaChiefArtificer(this);
    }
}
