
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.ManifestEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class WhisperwoodElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("face-up nontoken creatures you control");

    static {
        filter.add(Predicates.not(FaceDownPredicate.instance));
        filter.add(TokenPredicate.FALSE);
    }

    public WhisperwoodElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, manifest the top card of your library.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ManifestEffect(1), TargetController.YOU, false));
        
        // Sacrifice Whisperwood Elemental: Until end of turn, face-up, nontoken creatures you control gain "When this creature dies, manifest the top card of your library."
        Ability abilityToGain = new DiesSourceTriggeredAbility(new ManifestEffect(1));
        Effect effect = new GainAbilityControlledEffect(abilityToGain, Duration.EndOfTurn, filter);
        effect.setText("Until end of turn, face-up nontoken creatures you control gain \"When this creature dies, manifest the top card of your library.\"");
        this.addAbility(new SimpleActivatedAbility(
                Zone.ALL, effect, new SacrificeSourceCost()));
    }

    private WhisperwoodElemental(final WhisperwoodElemental card) {
        super(card);
    }

    @Override
    public WhisperwoodElemental copy() {
        return new WhisperwoodElemental(this);
    }
}
