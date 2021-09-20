package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantPlayerControlsPredicate;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfDeathsHold extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures enchanted player controls");

    static {
        filter.add(EnchantPlayerControlsPredicate.instance);
    }

    public CurseOfDeathsHold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Creatures enchanted player controls get -1/-1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                -1, -1, Duration.WhileOnBattlefield, filter, false
        )));
    }

    private CurseOfDeathsHold(final CurseOfDeathsHold card) {
        super(card);
    }

    @Override
    public CurseOfDeathsHold copy() {
        return new CurseOfDeathsHold(this);
    }
}
