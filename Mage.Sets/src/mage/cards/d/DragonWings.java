
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DragonWings extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with mana value 6 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public DragonWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)));
        // Cycling {1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{U}")));
        
        // When a creature with converted mana cost 6 or greater enters the battlefield, you may return Dragon Breath from your graveyard to the battlefield attached to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD, new DragonWingsEffect(), filter, true, SetTargetPointer.PERMANENT, null));
    }

    private DragonWings(final DragonWings card) {
        super(card);
    }

    @Override
    public DragonWings copy() {
        return new DragonWings(this);
    }
}

class DragonWingsEffect extends OneShotEffect {
    
    DragonWingsEffect() {
        super(Outcome.Benefit);
        this.staticText = "return {this} from your graveyard to the battlefield attached to that creature";
    }
    
    DragonWingsEffect(final DragonWingsEffect effect) {
        super(effect);
    }
    
    @Override
    public DragonWingsEffect copy() {
        return new DragonWingsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = (Card) source.getSourceObjectIfItStillExists(game);
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard != null && permanent != null && controller != null) {
            game.getState().setValue("attachTo:" + sourceCard.getId(), permanent);
            if (controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game)) {
                permanent.addAttachment(sourceCard.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}

