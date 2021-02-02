package mage.cards.c;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ConsignToDust extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or enchantments");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.ENCHANTMENT.getPredicate()));
    }

    public ConsignToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Strive - Consign to Dust costs 2G more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{G}"));

        // Destroy any number of target artifacts and/or enchantments.
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy any number of target artifacts and/or enchantments");
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, false);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(effect);
    }

    private ConsignToDust(final ConsignToDust card) {
        super(card);
    }

    @Override
    public ConsignToDust copy() {
        return new ConsignToDust(this);
    }
}
