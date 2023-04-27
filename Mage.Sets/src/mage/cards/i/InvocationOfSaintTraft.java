
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class InvocationOfSaintTraft extends CardImpl {

    public InvocationOfSaintTraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant Creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature attacks, create a 4/4 white Angel creature token with flying tapped
        // and attacking. Exile that token at end of combat."
        Ability gainedAbility = new AttacksTriggeredAbility(new InvocationOfSaintTraftEffect(), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA)));
    }

    private InvocationOfSaintTraft(final InvocationOfSaintTraft card) {
        super(card);
    }

    @Override
    public InvocationOfSaintTraft copy() {
        return new InvocationOfSaintTraft(this);
    }
}

class InvocationOfSaintTraftEffect extends OneShotEffect {

    InvocationOfSaintTraftEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create a 4/4 white Angel creature token with flying tapped and attacking. Exile that token at end of combat";
    }

    InvocationOfSaintTraftEffect(final InvocationOfSaintTraftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        CreateTokenEffect effect = new CreateTokenEffect(new AngelToken(), 1, true, true);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && (effect.apply(game, source))) {
            effect.exileTokensCreatedAtEndOfCombat(game, source);
            return true;
        }
        return false;
    }

    @Override
    public InvocationOfSaintTraftEffect copy() {
        return new InvocationOfSaintTraftEffect(this);
    }
}
