package mage.cards.i;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingVengeance extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public IncreasingVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Copy target instant or sorcery spell you control. If this spell was cast from a graveyard, copy that spell twice instead. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CopyTargetStackObjectEffect(false, false, true, 2, null),
                new CopyTargetStackObjectEffect(), CastFromGraveyardSourceCondition.instance, "copy target " +
                "instant or sorcery spell you control. If this spell was cast from a graveyard, " +
                "copy that spell twice instead. You may choose new targets for the copies"
        ));
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // Flashback {3}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private IncreasingVengeance(final IncreasingVengeance card) {
        super(card);
    }

    @Override
    public IncreasingVengeance copy() {
        return new IncreasingVengeance(this);
    }
}
