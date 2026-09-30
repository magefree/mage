package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class MurdocksCrusade extends CardImpl {

    static private final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("creature with toughness 4 or greater");
    static {
        creatureFilter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }
    static private final FilterEnchantmentPermanent enchantmentFilter = new FilterEnchantmentPermanent("enchantment with mana value 4 or greater");
    static {
        enchantmentFilter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public MurdocksCrusade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Teamwork 4
        this.addAbility(new TeamworkAbility(4));

        // Choose one. If this spell was cast using teamwork, choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If this spell was cast using teamwork, choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, TeamworkCondition.instance);

        // Street Justice
        // Exile target creature with toughness 4 or greater.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(creatureFilter));
        this.getSpellAbility().getModes().getMode().withFlavorWord("Steet Justice");

        // Legal Justice
        // Exile target enchantment with mana value 4 or greater.
        this.getSpellAbility().addMode(new Mode(new ExileTargetEffect()).withFlavorWord("Legal Justice")
                .addTarget(new TargetPermanent(enchantmentFilter)));
    }

    private MurdocksCrusade(final MurdocksCrusade card) {
        super(card);
    }

    @Override
    public MurdocksCrusade copy() {
        return new MurdocksCrusade(this);
    }
}
