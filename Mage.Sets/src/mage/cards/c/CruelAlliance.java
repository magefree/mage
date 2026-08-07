package mage.cards.c;

import java.util.UUID;

import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.ComparisonType;

/**
 *
 * @author nandmp
 */
public final class CruelAlliance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 3));
    }

    public CruelAlliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Teamwork 2
        this.addAbility(new TeamworkAbility(2));

        // Exile target creature with mana value 3 or less. If this spell was cast using teamwork, instead exile target creature and you gain 3 life.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(
                TeamworkCondition.instance, new TargetCreaturePermanent()
        ));

        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(3),
                TeamworkCondition.instance,
                "If this spell was cast using teamwork, instead exile target creature and you gain 3 life"
        ));
    }

    private CruelAlliance(final CruelAlliance card) {
        super(card);
    }

    @Override
    public CruelAlliance copy() {
        return new CruelAlliance(this);
    }
}
