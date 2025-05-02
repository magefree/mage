package mage.cards.o;

import mage.abilities.condition.common.BeheldDragonCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BeholdDragonAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OsseousExhale extends CardImpl {

    public OsseousExhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // As an additional cost to cast this spell, you may behold a Dragon.
        this.addAbility(new BeholdDragonAbility());

        // Osseous Exhale deals 5 damage to target attacking or blocking creature. If a Dragon was beheld, you gain 2 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(2), BeheldDragonCondition.instance,
                "if a Dragon was beheld, you gain 2 life"
        ));
    }

    private OsseousExhale(final OsseousExhale card) {
        super(card);
    }

    @Override
    public OsseousExhale copy() {
        return new OsseousExhale(this);
    }
}
