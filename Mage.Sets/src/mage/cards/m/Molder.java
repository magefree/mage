
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author LoneFox
 */
public final class Molder extends CardImpl {

    public Molder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Destroy target artifact or enchantment with converted mana cost X. It can't be regenerated. You gain X life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with converted mana cost X")));
        this.getSpellAbility().addEffect(new GainLifeEffect(new ManacostVariableValue()));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterArtifactOrEnchantmentPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment with converted mana cost X");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.addTarget(new TargetPermanent(filter));
        }
    }

    public Molder(final Molder card) {
        super(card);
    }

    @Override
    public Molder copy() {
        return new Molder(this);
    }
}
