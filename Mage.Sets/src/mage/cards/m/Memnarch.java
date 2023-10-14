package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Algid
 */
public final class Memnarch extends CardImpl {

    public Memnarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {1}{U}{U}: Target permanent becomes an artifact in addition to its other types.
        Effect effect = new AddCardTypeTargetEffect(Duration.Custom, CardType.ARTIFACT);
        effect.setText("Target permanent becomes an artifact in addition to its other types");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{U}{U}"));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // {3}{U}: Gain control of target artifact.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.WhileOnBattlefield), new ManaCostsImpl<>("{3}{U}"));
        ability2.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability2);
    }

    private Memnarch(final Memnarch card) {
        super(card);
    }

    @Override
    public Memnarch copy() {
        return new Memnarch(this);
    }
}
