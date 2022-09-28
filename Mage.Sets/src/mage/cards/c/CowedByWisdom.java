
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CowedByWisdom extends CardImpl {

    public CowedByWisdom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CowedByWisdomayCostToAttackBlockEffect()));
    }

    private CowedByWisdom(final CowedByWisdom card) {
        super(card);
    }

    @Override
    public CowedByWisdom copy() {
        return new CowedByWisdom(this);
    }
}

class CowedByWisdomayCostToAttackBlockEffect extends PayCostToAttackBlockEffectImpl {

    CowedByWisdomayCostToAttackBlockEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK);
        staticText = "Enchanted creature can't attack or block unless its controller pays {1} for each card in your hand";
    }

    CowedByWisdomayCostToAttackBlockEffect(CowedByWisdomayCostToAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getHand().isEmpty()) {
            ManaCosts manaCosts = new ManaCostsImpl<>();
            manaCosts.add(new GenericManaCost(controller.getHand().size()));
            return manaCosts;
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        return enchantment != null && enchantment.isAttachedTo(event.getSourceId());
    }

    @Override
    public CowedByWisdomayCostToAttackBlockEffect copy() {
        return new CowedByWisdomayCostToAttackBlockEffect(this);
    }

}
