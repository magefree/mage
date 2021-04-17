package mage.cards.m;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NoAbilityPredicate;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class MuragandaPetroglyphs extends CardImpl {

    private static final FilterCreaturePermanent filterNoAbilities
            = new FilterCreaturePermanent("creatures with no abilities");

    static {
        filterNoAbilities.add(NoAbilityPredicate.instance);
    }

    public MuragandaPetroglyphs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Creatures with no abilities get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield,
                filterNoAbilities, false
        )));
    }

    private MuragandaPetroglyphs(final MuragandaPetroglyphs card) {
        super(card);
    }

    @Override
    public MuragandaPetroglyphs copy() {
        return new MuragandaPetroglyphs(this);
    }
}
