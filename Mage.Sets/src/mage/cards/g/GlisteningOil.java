
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GlisteningOil extends CardImpl {

    public GlisteningOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}");
        this.subtype.add(SubType.AURA);

        
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature has infect.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(InfectAbility.getInstance(), AttachmentType.AURA)));
        
        // At the beginning of your upkeep, put a -1/-1 counter on enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GlisteningOilEffect(), TargetController.YOU, false));
        
        // When Glistening Oil is put into a graveyard from the battlefield, return Glistening Oil to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private GlisteningOil(final GlisteningOil card) {
        super(card);
    }

    @Override
    public GlisteningOil copy() {
        return new GlisteningOil(this);
    }
}

class GlisteningOilEffect extends OneShotEffect {
    GlisteningOilEffect() {
        super(Outcome.UnboostCreature);
        staticText = "put a -1/-1 counter on enchanted creature";
    }

    GlisteningOilEffect(final GlisteningOilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                creature.addCounters(CounterType.M1M1.createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }

    @Override
    public GlisteningOilEffect copy() {
        return new GlisteningOilEffect(this);
    }

}
