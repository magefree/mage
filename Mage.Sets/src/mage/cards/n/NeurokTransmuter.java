package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.LoseArtifactTypeTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noahg
 */
public final class NeurokTransmuter extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public NeurokTransmuter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}: Target creature becomes an artifact in addition to its other types until end of turn.
        Ability becomeArtifactAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT), new ManaCostsImpl<>("{U}"));
        becomeArtifactAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(becomeArtifactAbility);
        // {U}: Until end of turn, target artifact creature becomes blue and isn't an artifact.
        Effect blueEffect = new BecomesColorTargetEffect(ObjectColor.BLUE, Duration.EndOfTurn);
        blueEffect.setText("Until end of turn, target artifact creature becomes blue and ");
        Ability becomeBlueAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, blueEffect, new ManaCostsImpl<>("{U}"));
        becomeBlueAbility.addTarget(new TargetCreaturePermanent(filter));
        Effect loseArtifactEffect = new LoseArtifactTypeTargetEffect(Duration.EndOfTurn);
        loseArtifactEffect.setText("isn't an artifact");
        becomeBlueAbility.addEffect(loseArtifactEffect);
        this.addAbility(becomeBlueAbility);
    }

    private NeurokTransmuter(final NeurokTransmuter card) {
        super(card);
    }

    @Override
    public NeurokTransmuter copy() {
        return new NeurokTransmuter(this);
    }
}
