package mage.cards.c;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Crypsis extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("creatures your opponents control");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public Crypsis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature you control gains protection from creatures your opponents control until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter), Duration.EndOfTurn));
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap it.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

    }

    private Crypsis(final Crypsis card) {
        super(card);
    }

    @Override
    public Crypsis copy() {
        return new Crypsis(this);
    }
}
