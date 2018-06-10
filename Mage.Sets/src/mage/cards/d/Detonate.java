
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LoneFox
 */
public final class Detonate extends CardImpl {

    public Detonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // Destroy target artifact with converted mana cost X. It can't be regenerated. Detonate deals X damage to that artifact's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(new FilterArtifactPermanent("artifact with converted mana cost X")));
        Effect effect = new DamageTargetControllerEffect(new ManacostVariableValue());
        effect.setText("{this} deals X damage to that artifact's controller");
        this.getSpellAbility().addEffect(effect);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact with converted mana cost X");
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.addTarget(new TargetArtifactPermanent(filter));
        }
    }

    public Detonate(final Detonate card) {
        super(card);
    }

    @Override
    public Detonate copy() {
        return new Detonate(this);
    }
}
