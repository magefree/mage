
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author LevelX2
 */
public final class AustereCommand extends CardImpl {

    private static final FilterCreaturePermanent filter3orLess = new FilterCreaturePermanent("creatures with mana value 3 or less");
    private static final FilterCreaturePermanent filter4orMore = new FilterCreaturePermanent("creatures with mana value 4 or greater");
    static {
        filter3orLess.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        filter4orMore.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public AustereCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}{W}");


        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Destroy all artifacts;
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterArtifactPermanent("artifacts")));
        // or destroy all enchantments;
        Mode mode = new Mode(new DestroyAllEffect(new FilterEnchantmentPermanent("enchantments")));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy all creatures with converted mana cost 3 or less;
        mode = new Mode(new DestroyAllEffect(filter3orLess));
        this.getSpellAbility().getModes().addMode(mode);
        // or destroy all creatures with converted mana cost 4 or greater.
        mode = new Mode(new DestroyAllEffect(filter4orMore));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private AustereCommand(final AustereCommand card) {
        super(card);
    }

    @Override
    public AustereCommand copy() {
        return new AustereCommand(this);
    }
}
