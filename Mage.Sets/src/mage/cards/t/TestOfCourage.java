package mage.cards.t;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInGraveyard;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class TestOfCourage extends CardImpl {

    private static final FilterArtifactOrEnchantmentPermanent filter =
        new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public TestOfCourage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose one --
        // * Alter the Past -- Exile target card from a graveyard. You gain 2 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().withFirstModeFlavorWord("Alter the Past");

        // * Obliterate the Transwarp Network -- Destroy target artifact or enchantment with mana value 3 or less.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter));
        mode.withFlavorWord("Obliterate the Transwarp Network");
        this.getSpellAbility().addMode(mode);
    }

    private TestOfCourage(final TestOfCourage card) {
        super(card);
    }

    @Override
    public TestOfCourage copy() {
        return new TestOfCourage(this);
    }
}
