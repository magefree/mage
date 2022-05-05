
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.permanent.token.GremlinToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class GremlinInfestation extends CardImpl {

    public GremlinInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your end step, Gremlin Infestation deals 2 damage to enchanted artifact's controller.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DamageAttachedControllerEffect(2).setText("{this} deals 2 damage to enchanted artifact's controller"), TargetController.YOU, false));

        // When enchanted artifact is put into a graveyard, create a 2/2 red Gremlin creature token.
        this.addAbility(new DiesAttachedTriggeredAbility(new CreateTokenEffect(new GremlinToken()), "enchanted artifact", false, false));
    }

    private GremlinInfestation(final GremlinInfestation card) {
        super(card);
    }

    @Override
    public GremlinInfestation copy() {
        return new GremlinInfestation(this);
    }
}
